package com.castle.fortress.admin.check.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.check.entity.*;
import com.castle.fortress.admin.es.EsSearchService;
import com.castle.fortress.admin.knowledge.service.KbDownloadConfService;
import com.castle.fortress.admin.knowledge.utis.ConcurrentStopWatch;
import com.castle.fortress.admin.knowledge.utis.PdfBoxUtil;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.check.dto.KbDuplicateCheckDto;
import com.castle.fortress.admin.check.mapper.KbDuplicateCheckMapper;
import com.castle.fortress.admin.check.service.KbDuplicateCheckService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
/**
 * 知识库查重表 服务实现类
 *
 * @author
 * @since 2023-07-15
 */
@Slf4j
@Service
public class KbDuplicateCheckServiceImpl extends ServiceImpl<KbDuplicateCheckMapper, KbDuplicateCheckEntity> implements KbDuplicateCheckService {
    @Autowired
    private ConfigOssService ossService;
    @Autowired
    private KbDownloadConfService kbDownloadConfService;

    /**
     * 获取查询条件
     *
     * @param kbDuplicateCheckDto
     * @return
     */
    public QueryWrapper<KbDuplicateCheckEntity> getWrapper(KbDuplicateCheckDto kbDuplicateCheckDto) {
        QueryWrapper<KbDuplicateCheckEntity> wrapper = new QueryWrapper();
        if (kbDuplicateCheckDto != null) {
            KbDuplicateCheckEntity kbDuplicateCheckEntity = ConvertUtil.transformObj(kbDuplicateCheckDto, KbDuplicateCheckEntity.class);
            wrapper.like(StrUtil.isNotEmpty(kbDuplicateCheckEntity.getTitle()), "title", kbDuplicateCheckEntity.getTitle());
            wrapper.like(StrUtil.isNotEmpty(kbDuplicateCheckEntity.getAuthor()), "author", kbDuplicateCheckEntity.getAuthor());
            wrapper.eq(kbDuplicateCheckEntity.getStatus() != null, "status", kbDuplicateCheckEntity.getStatus());
            wrapper.eq(kbDuplicateCheckEntity.getUploadType() != null, "upload_type", kbDuplicateCheckEntity.getUploadType());
            wrapper.like(StrUtil.isNotEmpty(kbDuplicateCheckEntity.getType()), "type", kbDuplicateCheckEntity.getType());
            wrapper.ge(kbDuplicateCheckDto.getStartTime() != null, "create_time", kbDuplicateCheckDto.getStartTime())
                    .lt(kbDuplicateCheckDto.getEndTime() != null, "create_time", kbDuplicateCheckDto.getEndTime());
        }
        wrapper.orderByDesc("create_time");
        return wrapper;
    }


    @Override
    public IPage<KbDuplicateCheckDto> pageKbDuplicateCheck(Page<KbDuplicateCheckDto> page, KbDuplicateCheckDto kbDuplicateCheckDto) {
        QueryWrapper<KbDuplicateCheckEntity> wrapper = getWrapper(kbDuplicateCheckDto);
        wrapper.eq("create_user",kbDuplicateCheckDto.getCreateUser());
        Page<KbDuplicateCheckEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbDuplicateCheckEntity> kbDuplicateCheckPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbDuplicateCheckDto> pageDto = new Page(kbDuplicateCheckPage.getCurrent(), kbDuplicateCheckPage.getSize(), kbDuplicateCheckPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbDuplicateCheckPage.getRecords(), KbDuplicateCheckDto.class));
        return pageDto;
    }


    @Override
    public List<KbDuplicateCheckDto> listKbDuplicateCheck(KbDuplicateCheckDto kbDuplicateCheckDto) {
        QueryWrapper<KbDuplicateCheckEntity> wrapper = getWrapper(kbDuplicateCheckDto);
        List<KbDuplicateCheckEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbDuplicateCheckDto.class);
    }

    @Override
    public void fileShow(HttpServletResponse response, String id) {
        KbDuplicateCheckEntity kbDuplicateCheckEntity = baseMapper.selectById(id);
        // 一个文件不需要进行压缩处理
        if (kbDuplicateCheckEntity == null) {
            throw new BizException("传入的数据有误");
        }
        String pdfPath = kbDuplicateCheckEntity.getPdfPath();
        InputStream inputStream = null;
        if (StrUtil.isEmpty(pdfPath)) {
            // 如果文件不存在 说明在转换中 给一个磨人的
            inputStream = this.getClass().getClassLoader().getResourceAsStream("default.pdf");
        }
        String fileName = pdfPath;
        response.reset();
        response.setContentType("application/octet-stream;charset=utf-8");
        response.setHeader("Content-disposition", "attachment; filename=" + fileName);
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream == null ? new FileInputStream(ossService.getFilePathPrefix() + pdfPath) : inputStream);
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            int len;
            byte[] buff = new byte[1024];
            while ((len = bis.read(buff)) > 0) {
                bos.write(buff, 0, len);
            }
            System.out.println("\n---读取完毕！---");
            bis.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void downloadFile(HttpServletResponse response, String id, String userName) {
        ConcurrentStopWatch stopWatch = new ConcurrentStopWatch("查重下载");
        stopWatch.start("查询db数据");
        KbDuplicateCheckEntity kbDuplicateCheckEntity = baseMapper.selectById(id);
        if (kbDuplicateCheckEntity == null) {
            throw new BizException("传入的数据有误");
        }
        stopWatch.stop("查询db数据");
        stopWatch.start("开始下载");
        if (StrUtil.isEmpty(kbDuplicateCheckEntity.getPdfPath())) {
            throw new BizException("PDF地址为空");
        }
        String filePathPrefix = ossService.getFilePathPrefix();
        String pdfPath=filePathPrefix+kbDuplicateCheckEntity.getPdfPath();
        boolean isWatermark = kbDownloadConfService.isWatermark();
        if (isWatermark) {
            stopWatch.start("开启水印下载");
            PdfBoxUtil pdfBoxUtil = new PdfBoxUtil();
            pdfPath = com.castle.fortress.admin.knowledge.utis.FileUtil.getWatermarkPath(filePathPrefix + kbDuplicateCheckEntity.getPdfPath(), userName);
            if (!com.castle.fortress.admin.knowledge.utis.FileUtil.isFileExist(pdfPath)) {
                try {
                    pdfBoxUtil.watermarkPDF(filePathPrefix +kbDuplicateCheckEntity.getPdfPath(), userName, pdfPath);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            stopWatch.stop("开启水印下载");
        }
        com.castle.fortress.admin.knowledge.utis.FileUtil.downloadFile(response, Arrays.asList(pdfPath));
        stopWatch.stop("开始下载");
        log.debug(String.valueOf(stopWatch.getTotalTimeMillis()));
        log.debug(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    public static BigDecimal getPercentage(String number, String sum) {
        double tmpV = Double.parseDouble(number);
        double tmpN = Double.parseDouble(sum);
        return new BigDecimal((tmpV / tmpN) * 100).setScale(1, BigDecimal.ROUND_HALF_UP);
    }

}

