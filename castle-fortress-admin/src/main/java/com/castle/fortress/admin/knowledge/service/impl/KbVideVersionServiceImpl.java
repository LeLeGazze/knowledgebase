package com.castle.fortress.admin.knowledge.service.impl;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.castle.fortress.admin.knowledge.dto.KbModelAcceptanceDto;
import com.castle.fortress.admin.knowledge.dto.KbPropertyDesignDto;
import com.castle.fortress.admin.knowledge.entity.KbModelEntity;
import com.castle.fortress.admin.knowledge.enums.FileTypeEnum;
import com.castle.fortress.admin.knowledge.enums.FilesContentEnum;
import com.castle.fortress.admin.knowledge.enums.FilesPreviewEnum;
import com.castle.fortress.admin.knowledge.enums.FromTypeEnum;
import com.castle.fortress.admin.knowledge.service.*;
import com.castle.fortress.admin.knowledge.service.impl.file.FilePreviewFactory;
import com.castle.fortress.admin.knowledge.utis.ConcurrentStopWatch;
import com.castle.fortress.admin.knowledge.utis.DiffHandleUtils;
import com.castle.fortress.admin.knowledge.utis.FileUtil;
import com.castle.fortress.admin.knowledge.utis.PdfBoxUtil;
import com.castle.fortress.admin.system.dto.ConfigOssDto;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.admin.utils.BizCommonUtil;
import com.castle.fortress.admin.utils.HttpUtil;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.utils.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.entity.KbVideVersionEntity;
import com.castle.fortress.admin.knowledge.dto.KbVideVersionDto;
import com.castle.fortress.admin.knowledge.mapper.KbVideVersionMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * PDF/word 等转换成PDF 服务实现类
 *
 * @author
 * @since 2023-05-08
 */
@Service
@Slf4j
public class KbVideVersionServiceImpl extends ServiceImpl<KbVideVersionMapper, KbVideVersionEntity> implements KbVideVersionService {

    private static final int BUFFER_SIZE = 2 * 1024;
    @Autowired
    private KbModelService kbModelService;

    @Autowired
    private KbColConfigService kbColConfigService;

    @Autowired
    private KbModelDataService kbModelDataService;
    @Autowired
    private ConfigOssService configOssService;

    @Value("${pdf.url}")
    private String PDFURL;
    @Autowired
    private KbDownloadConfService kbDownloadConfService;

    @Autowired
    private FilePreviewFactory filePreviewFactory;


    /**
     * 获取查询条件
     *
     * @param kbVideVersionDto
     * @return
     */
    public QueryWrapper<KbVideVersionEntity> getWrapper(KbVideVersionDto kbVideVersionDto) {
        QueryWrapper<KbVideVersionEntity> wrapper = new QueryWrapper();
        if (kbVideVersionDto != null) {
            KbVideVersionEntity kbVideVersionEntity = ConvertUtil.transformObj(kbVideVersionDto, KbVideVersionEntity.class);
            wrapper.like(StrUtil.isNotEmpty(kbVideVersionEntity.getFileUrl()), "file_url", kbVideVersionEntity.getFileUrl());
            wrapper.like(StrUtil.isNotEmpty(kbVideVersionEntity.getFileName()), "file_name", kbVideVersionEntity.getFileName());
//            wrapper.like(StrUtil.isNotEmpty(kbVideVersionEntity.getUrl()), "url", kbVideVersionEntity.getUrl());
            wrapper.like(kbVideVersionEntity.getBId() != null, "b_id", kbVideVersionEntity.getBId());
            wrapper.like(StrUtil.isNotEmpty(kbVideVersionEntity.getFId()), "f_id", kbVideVersionEntity.getFId());
            wrapper.like(StrUtil.isNotEmpty(kbVideVersionEntity.getFileSize()), "file_size", kbVideVersionEntity.getFileSize());
            wrapper.like(StrUtil.isNotEmpty(kbVideVersionEntity.getType()), "type", kbVideVersionEntity.getType());
            wrapper.like(kbVideVersionEntity.getStatus() != null, "ststus", kbVideVersionEntity.getStatus());
            wrapper.orderByDesc("create_time");
        }
        return wrapper;
    }


    @Override
    public IPage<KbVideVersionDto> pageKbVideVersion(Page<KbVideVersionDto> page, KbVideVersionDto kbVideVersionDto) {
        QueryWrapper<KbVideVersionEntity> wrapper = getWrapper(kbVideVersionDto);
        Page<KbVideVersionEntity> pageEntity = new Page(page.getCurrent(), page.getSize());
        Page<KbVideVersionEntity> kbVideVersionPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<KbVideVersionDto> pageDto = new Page(kbVideVersionPage.getCurrent(), kbVideVersionPage.getSize(), kbVideVersionPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(kbVideVersionPage.getRecords(), KbVideVersionDto.class));
        return pageDto;
    }

    @Override
    public IPage<KbVideVersionDto> pageKbVideVersionExtends(Page<KbVideVersionDto> page, KbVideVersionDto kbVideVersionDto) {
        Map<String, Long> pageMap = BizCommonUtil.getPageParam(page);
        KbVideVersionEntity kbVideVersionEntity = ConvertUtil.transformObj(kbVideVersionDto, KbVideVersionEntity.class);
        List<KbVideVersionEntity> kbVideVersionList = baseMapper.extendsList(pageMap, kbVideVersionEntity);
        Long total = baseMapper.extendsCount(kbVideVersionEntity);
        Page<KbVideVersionDto> pageDto = new Page(page.getCurrent(), page.getSize(), total);
        pageDto.setRecords(ConvertUtil.transformObjList(kbVideVersionList, KbVideVersionDto.class));
        return pageDto;
    }

    @Override
    public KbVideVersionDto getByIdExtends(Long id) {
        KbVideVersionEntity kbVideVersionEntity = baseMapper.getByIdExtends(id);
        return ConvertUtil.transformObj(kbVideVersionEntity, KbVideVersionDto.class);
    }

    @Override
    public List<KbVideVersionDto> listKbVideVersion(KbVideVersionDto kbVideVersionDto) {
        QueryWrapper<KbVideVersionEntity> wrapper = getWrapper(kbVideVersionDto);
        List<KbVideVersionEntity> list = baseMapper.selectList(wrapper);
        return ConvertUtil.transformObjList(list, KbVideVersionDto.class);
    }

    @Override
    @Async
    public void tmpSave(KbModelAcceptanceDto formDataDto) {
        // 获取文件路径
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = configOssService.selectBySelective(ftDto);
        ConfigOssDto configOssDto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(configOssDto.getPtConfig(), OssPlatFormDto.class);
        String filePositon = ossPlatFormDto.getLocalFilePosition();
        String fileUrl = ossPlatFormDto.getLocalFileUrl();
        List<String> typeList = Arrays.stream(FileTypeEnum.values()).map(x -> x.getName()).collect(Collectors.toList());
        List<Object> attachmentList = formDataDto.getAttachments();
//        ArrayList<String> tmpAtttachmentLIST = new ArrayList<>();
//        attachment.forEach(item->{
//
//            tmpAtttachmentLIST.add(String.valueOf(jsonObject.get("url")));
//        });
//
//        List<String> attachmentList = tmpAtttachmentLIST.stream().filter(iter -> {
//                    boolean res = false;
//                    if (typeList.contains(iter.substring(iter.lastIndexOf(".") + 1))) {
//                        res = true;
//                    }
//                    return res;
//                }
//        ).collect(Collectors.toList());
        //  基础信息

        for (Object item : attachmentList) {
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(item));
            String fileName = jsonObject.getString("url").replace(fileUrl, filePositon + "/");
            KbVideVersionEntity kbVideVersionEntity = new KbVideVersionEntity();
            kbVideVersionEntity.setBId(formDataDto.getId()); // 文章id
            kbVideVersionEntity.setFId(null); //文章MD5直
            kbVideVersionEntity.setFileUrl(fileName);
            kbVideVersionEntity.setStatus(4); // 插入成功
            kbVideVersionEntity.setType("基础");
            kbVideVersionEntity.setAccessPath(fileUrl);
            kbVideVersionEntity.setFileName(jsonObject.getString("name"));
            baseMapper.insert(kbVideVersionEntity);
        }
        // 扩展

        KbModelEntity modelEntity = kbModelService.getById(formDataDto.getModelId());
        KbPropertyDesignDto kbPropertyDesignDto = new KbPropertyDesignDto();
        kbPropertyDesignDto.setModelId(formDataDto.getModelId());

        List<KbPropertyDesignDto> kbPropertyDesignList = kbColConfigService.listKbColConfig(kbPropertyDesignDto);
        List<KbPropertyDesignDto> collect = kbPropertyDesignList.stream().filter(item -> item.getFormType().equals(FromTypeEnum.attachment.getCode())).collect(Collectors.toList());
        Map<String, Object> queryByDataId = kbModelDataService.queryByDataId(modelEntity.getCode(), formDataDto.getId());
        collect.forEach(item -> {
            String url = String.valueOf(queryByDataId.get(item.getPropName()));
            List<JSONObject> fujianJson = JSONArray.parseArray(url, JSONObject.class);
            if (fujianJson != null && fujianJson.size() > 0) {
                for (JSONObject itemJson : fujianJson) {
                    String s = itemJson.getString("url");
//                    if (typeList.contains(s.substring(s.lastIndexOf(".") + 1))) {
                    String fileName = s.replace(fileUrl, filePositon + "/");
                    KbVideVersionEntity kbVideVersionEntity = new KbVideVersionEntity();
                    kbVideVersionEntity.setBId(formDataDto.getId()); // 文章id
                    kbVideVersionEntity.setFId(null); //文章MD5直
                    kbVideVersionEntity.setFileUrl(fileName);
                    kbVideVersionEntity.setStatus(4); // 插入成功
                    kbVideVersionEntity.setType("扩展:" + item.getName());
                    kbVideVersionEntity.setAccessPath(fileUrl);
                    kbVideVersionEntity.setFileName(itemJson.getString("name"));
                    baseMapper.insert(kbVideVersionEntity);
//                    }
                }
            }
        });
        HashMap<String, String> headers = new HashMap<>();
        HashMap<String, String> queryParams = new HashMap<>();
        HashMap<String, Object> bodyMap = new HashMap<>();
        queryParams.put("bid", String.valueOf(formDataDto.getId()));
        try {
            HttpUtil.doRequest("get", PDFURL + "/knowledge/kbVideVersion/toLocalPngBid", headers, queryParams, bodyMap);
        } catch (Exception e) {
        } finally {
            log.debug("调用转换png 成功 ：{}", formDataDto.getId());
        }

    }

    @Override
    @Async
    public void tmpUpdate(Long id, KbModelAcceptanceDto formDataDto) {
        // 删除数据库已经存在的数据
        baseMapper.updateByBid(id, formDataDto.getId());
        this.tmpSave(formDataDto);
    }

    @Override
    public void downloadFile(HttpServletResponse response, Long basicId, String userName, List<Long> vides) {
        StopWatch stopWatch = new StopWatch("定时修改es标签Task");
        stopWatch.start("查询db数据");
        QueryWrapper<KbVideVersionEntity> wrapper = new QueryWrapper();
        wrapper.eq("b_id", basicId);
        wrapper.in("id", vides);
        List<KbVideVersionEntity> entityList = baseMapper.selectList(wrapper);
        stopWatch.stop();
        List<String> fileListPath = new ArrayList<>();
        // 校验下载的是源文件还是PDF
        boolean isSourceFile = kbDownloadConfService.isSourceFile();
        if (isSourceFile) {
            stopWatch.start("开启源文件下载");
            fileListPath = entityList.stream().map(item -> item.getFileUrl()).collect(Collectors.toList());
            stopWatch.stop();
        }
        // 校验是否开启水印 开启水印必须是PDF类型
        boolean isWatermark = kbDownloadConfService.isWatermark();
        if (isWatermark) {
            stopWatch.start("开启水印下载");
            PdfBoxUtil pdfBoxUtil = new PdfBoxUtil();
            fileListPath = entityList.stream().map(item -> {
                String watermarkPath = FileUtil.getWatermarkPath(item.getDownloadUrl(), userName);
                if (!FileUtil.isFileExist(watermarkPath)) {
                    try {
                        pdfBoxUtil.watermarkPDF(item.getDownloadUrl(), userName, watermarkPath);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                return watermarkPath;
            }).collect(Collectors.toList());
            stopWatch.stop();
        }

        // 如果目标文件为空 下载PDF文件
        if (fileListPath.size() == 0) {
            stopWatch.start("下载PDF文件");
            fileListPath = entityList.stream().map(item -> item.getDownloadUrl()).collect(Collectors.toList());
            stopWatch.stop();
        }
        FileUtil.downloadFile(response, fileListPath);
        log.debug(String.valueOf(stopWatch.getTotalTimeMillis()));
        log.debug(stopWatch.prettyPrint(TimeUnit.MILLISECONDS));
    }

    @Override
    public void fileShow(HttpServletResponse response, Long basicId, List<Long> vides) {
        QueryWrapper<KbVideVersionEntity> wrapper = new QueryWrapper();
        wrapper.eq("b_id", basicId);
        // 一个文件不需要进行压缩处理
        if (vides.size() == 1) {
            Long videId = vides.get(0);
            wrapper.eq("id", videId);
            KbVideVersionEntity kbVideVersionEntity = baseMapper.selectOne(wrapper);
            String fileUrl = kbVideVersionEntity.getDownloadUrl();
            InputStream inputStream = null;
            if (fileUrl == null) {
                // 如果文件不存在 说明在转换中 给一个磨人的
                inputStream = this.getClass().getClassLoader().getResourceAsStream("default.pdf");
            }
            String fileName = kbVideVersionEntity.getFileName().replace(StringUtils.getFilenameExtension(kbVideVersionEntity.getFileName()), "pdf");
            response.reset();
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            try {
                BufferedInputStream bis = new BufferedInputStream(inputStream == null ? new FileInputStream(fileUrl) : inputStream);
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

    }

    @Override
    public List<KbVideVersionDto> findByTypeVersion(KbVideVersionDto kbVideVersionDto) {
        return baseMapper.findByTypeVersion(kbVideVersionDto);
    }

    @Override
    public Object getContentComparison(Model model, String sourceId, String targetId) {
        ConcurrentStopWatch concurrentStopWatch = new ConcurrentStopWatch("文件内容对比");
        concurrentStopWatch.start("查询原始文数据库");
        KbVideVersionEntity videVersionEntity = baseMapper.selectById(Long.parseLong(sourceId));
        if (videVersionEntity == null) {
            log.debug("传入的源文件有误，未查询到相关内容：{}", sourceId);
            return false;
        }
        List<String> sourceData = null;
        try {
            concurrentStopWatch.stop("查询原始文数据库");
            FilesContentEnum filesTypeEnum = FilesContentEnum.typeFromFileName(videVersionEntity.getFileName());
            String sourceInstanceName = filesTypeEnum.getInstanceName();
            concurrentStopWatch.start("解析原始文件内容");
            FileContentComparison sourceFileContentComparison = filePreviewFactory.get(sourceInstanceName);
            concurrentStopWatch.stop("解析原始文件内容");
            sourceData = sourceFileContentComparison.fileContentHandle(videVersionEntity.getFileUrl());
            if (sourceId.equals(targetId)) {
                concurrentStopWatch.start("传入的id相同");
                List<String> list = DiffHandleUtils.diffString(sourceData, sourceData, videVersionEntity.getFileName(), videVersionEntity.getFileName());
                String msg = DiffHandleUtils.generateDiffHtml(list);
                model.addAttribute("msg", "`" + msg + "`");
                model.addAttribute("sourceFile", videVersionEntity.getFileName());
                model.addAttribute("targetFile", videVersionEntity.getFileName());
                concurrentStopWatch.stop("传入的id相同");
                log.debug(concurrentStopWatch.prettyPrint(TimeUnit.MILLISECONDS));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return videVersionEntity.getFileName();
        }

        // 传入的不相同
        concurrentStopWatch.start("查询目标文件数");
        KbVideVersionEntity targetEntity = baseMapper.selectById(Long.parseLong(targetId));
        if (targetEntity == null) {
            log.debug("传入的目标文件有误，未查询到相关内容：{}", targetId);
            return false;
        }
        try {
            concurrentStopWatch.stop("查询目标文件数");
            concurrentStopWatch.start("解析目标文件数据内容");
            FilesContentEnum targetFilesType = FilesContentEnum.typeFromFileName(targetEntity.getFileName());
            String targetInstanceName = targetFilesType.getInstanceName();
            FileContentComparison targetFileContentComparison = filePreviewFactory.get(targetInstanceName);
            List<String> targetData = targetFileContentComparison.fileContentHandle(targetEntity.getFileUrl());
            concurrentStopWatch.stop("解析目标文件数据内容");
            concurrentStopWatch.start("文件内容对比");
            List<String> list = DiffHandleUtils.diffString(sourceData, targetData, videVersionEntity.getFileName(), targetEntity.getFileName());
            String msg = DiffHandleUtils.generateDiffHtml(list);
            model.addAttribute("msg", "`" + msg + "`");
            model.addAttribute("sourceFile", videVersionEntity.getFileName());
            model.addAttribute("targetFile", targetEntity.getFileName());
            concurrentStopWatch.stop("文件内容对比");
            log.debug(concurrentStopWatch.prettyPrint(TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
            return targetEntity.getFileName();
        }
        return true;
    }

    @Override
    public String getFilePreview(Model model, String id) {
        ConcurrentStopWatch concurrentStopWatch = new ConcurrentStopWatch("文件内容预览");
        concurrentStopWatch.start("获取数据库数据");
        KbVideVersionEntity videVersionEntity = baseMapper.selectById(Long.parseLong(id));
        if (videVersionEntity == null) {
            return "error";
        }
        concurrentStopWatch.stop("获取数据库数据");
        //获取对应的实现类
        concurrentStopWatch.start("获取文件内容");
        FilesPreviewEnum filesPreviewEnum = FilesPreviewEnum.typeFromFileName(videVersionEntity.getFileName());
        String instanceName = filesPreviewEnum.getInstanceName();
        if (instanceName == null){
            model.addAttribute("errorMsg", "不支持的文件类型");
            return "notSupported.ftl";
        }
        FilePreview preview = filePreviewFactory.getPreview(instanceName);
        String filePreviewHandle = preview.filePreviewHandle(model,videVersionEntity.getFileUrl());
        model.addAttribute("fileName", videVersionEntity.getFileName());
        log.debug(concurrentStopWatch.prettyPrint(TimeUnit.MILLISECONDS));
        concurrentStopWatch.stop("获取文件内容");
        return filePreviewHandle;
    }

    @Override
    public String getfilePreviewBase64(String id) {
        ConcurrentStopWatch concurrentStopWatch = new ConcurrentStopWatch("文件内容预览");
        concurrentStopWatch.start("获取数据库数据");
        KbVideVersionEntity videVersionEntity = baseMapper.selectById(Long.parseLong(id));
        if (videVersionEntity == null) {
            return "error";
        }
        concurrentStopWatch.stop("获取数据库数据");
        //获取对应的实现类
        concurrentStopWatch.start("获取文件内容");
        FilesPreviewEnum filesPreviewEnum = FilesPreviewEnum.typeFromFileName(videVersionEntity.getFileName());
        String instanceName = filesPreviewEnum.getInstanceName();
        FilePreview preview = filePreviewFactory.getPreview(instanceName);
        String filePreviewHandleData = preview.filePreviewHandle(null,videVersionEntity.getFileUrl());
        concurrentStopWatch.stop("获取文件内容");
        log.debug(concurrentStopWatch.prettyPrint(TimeUnit.MILLISECONDS));
        return filePreviewHandleData;
    }
}

