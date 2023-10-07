package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.castle.fortress.admin.knowledge.enums.FileTypeEnum;
import com.castle.fortress.admin.knowledge.service.IJcsegService;
import com.castle.fortress.admin.knowledge.service.impl.JcsegServiceImpl;
import com.castle.fortress.admin.system.dto.ConfigOssDto;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.dto.SysOssRecordDto;
import com.castle.fortress.admin.system.entity.ConfigOssEntity;
import com.castle.fortress.admin.system.mapper.ConfigOssMapper;
import com.castle.fortress.admin.system.service.ConfigOssService;
import com.castle.fortress.admin.system.service.OssService;
import com.castle.fortress.admin.system.service.SysOssRecordService;
import com.castle.fortress.admin.utils.FortressParseUtil;
import com.castle.fortress.admin.utils.Md5;
import com.castle.fortress.admin.utils.PicUtil;
import com.castle.fortress.admin.utils.RedisUtils;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OssEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import com.qcloud.cos.utils.Md5Utils;
import org.apache.commons.io.FileUtils;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.WebAsyncTask;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 存储配置服务实现类
 *
 * @author castle
 */
@Service
public class ConfigOssServiceImpl extends ServiceImpl<ConfigOssMapper, ConfigOssEntity> implements ConfigOssService {
    @Autowired
    private OssService ossService;

    @Autowired
    private SysOssRecordService sysOssRecordService;

    @Autowired
    private JcsegServiceImpl jcsegServiceImpl;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public IPage<ConfigOssDto> pageConfigFt(Page<ConfigOssDto> page, ConfigOssDto ftDto) {
        QueryWrapper<ConfigOssEntity> wrapper = new QueryWrapper<>();
        if (ftDto != null) {
            wrapper.eq(ftDto.getPlatform() != null, "platform", ftDto.getPlatform());
            wrapper.eq(ftDto.getStatus() != null, "status", ftDto.getStatus());
            wrapper.like(StrUtil.isNotEmpty(ftDto.getFtCode()), "ft_code", ftDto.getFtCode());
        }
        wrapper.orderByDesc("create_time");
        Page<ConfigOssEntity> pageEntity = new Page<>(page.getCurrent(), page.getSize());
        Page<ConfigOssEntity> configFtPage = baseMapper.selectPage(pageEntity, wrapper);
        Page<ConfigOssDto> pageDto = new Page<>(configFtPage.getCurrent(), configFtPage.getSize(), configFtPage.getTotal());
        pageDto.setRecords(ConvertUtil.transformObjList(configFtPage.getRecords(), ConfigOssDto.class));
        return pageDto;
    }

    @Override
    public List<ConfigOssDto> selectBySelective(ConfigOssDto ftDto) {
        QueryWrapper<ConfigOssEntity> wrapper = new QueryWrapper<>();
        if (ftDto != null) {
            wrapper.eq(ftDto.getPlatform() != null, "platform", ftDto.getPlatform());
            wrapper.eq(ftDto.getStatus() != null, "status", ftDto.getStatus());
        }
        return ConvertUtil.transformObjList(baseMapper.selectList(wrapper), ConfigOssDto.class);

    }

    @Override
    public void updataStatusNo() {
        baseMapper.updateStatusNo();
    }

    @Override
    public RespBody putFile(byte[] content) {
        return null;
    }

    @Override
    public RespBody putFile(File file) {
        return null;
    }

    @Override
    public RespBody putFile(MultipartFile file) {
        RespBody rb = new RespBody();
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = this.selectBySelective(ftDto);
        if (ftDtoList == null || ftDtoList.size() != 1) {
            throw new BizException(BizErrorCode.FT_CONFIG_ERROR);
        }
        ConfigOssDto configOssDto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(configOssDto.getPtConfig(), OssPlatFormDto.class);
        String originalName = "", url = "", path = "";
        try {
            originalName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //本地存储
        if (OssEnum.SYS_LOCAL.getCode().equals(configOssDto.getPlatform())) {
            //该文件路径需要做nginx映射 参照帮助文档
            String filePositon = ossPlatFormDto.getLocalFilePosition();//"E:\\product\\fileStore";
            String fileUrl = ossPlatFormDto.getLocalFileUrl();//"http://localhost/image/";
            String suffix = FileUtil.extName(originalName);
            String date = DateUtil.today().replace("-", "");
            path = "upload/" + date + "/" + System.currentTimeMillis() + (StrUtil.isEmpty(suffix) ? "" : ("." + suffix));
            url = fileUrl + path;
            File targetFile = new File(filePositon + "/" + path);
            ByteArrayInputStream bis = null;
            byte[] bytes = null;
            try {

                if (com.castle.fortress.common.utils.FileUtils.isImage(file)) {
                    bytes = PicUtil.compressPic(file, 12, 350);
                } else {
                    bytes = file.getBytes();
                }
                bis = new ByteArrayInputStream(bytes);
                FileUtils.copyToFile(bis, targetFile);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                    }
                }
            }
            Map<String, Object> map = new HashMap<>(1);
            map.put("name", originalName);
            //页面访问路径
            map.put("url", url);
            //数据库存储路径
            map.put("path", path);
            try {
                map.put("md5", Md5.bytesToHex(Md5Utils.computeMD5Hash(new ByteArrayInputStream(bytes))));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            rb.setData(map);
            rb.setSuccess(true);
            //阿里云存储
        } else {
            rb = ossService.putFile(file);
        }
        if (rb.isSuccess()) {
            Map<String, String> m = (Map<String, String>) rb.getData();
            SysOssRecordDto dto = new SysOssRecordDto();
            dto.setResourceName(m.get("name"));
            dto.setOssPlatform(configOssDto.getPlatform());
            dto.setResourceUrl(m.get("path"));
            sysOssRecordService.saveAsyn(dto);
        }
        return rb;

    }

    @Override
    public RespBody putFileRead(MultipartFile file) {


        RespBody rb = new RespBody();
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = this.selectBySelective(ftDto);
        if (ftDtoList == null || ftDtoList.size() != 1) {
            throw new BizException(BizErrorCode.FT_CONFIG_ERROR);
        }
        ConfigOssDto configOssDto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(configOssDto.getPtConfig(), OssPlatFormDto.class);
        String originalName = "", url = "", path = "";
        try {
            originalName = new String(file.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String md5 = "";
        //本地存储
        if (OssEnum.SYS_LOCAL.getCode().equals(configOssDto.getPlatform())) {
            //该文件路径需要做nginx映射 参照帮助文档
            String filePositon = ossPlatFormDto.getLocalFilePosition();//"E:\\product\\fileStore";
            String fileUrl = ossPlatFormDto.getLocalFileUrl();//"http://localhost/image/";
            String suffix = FileUtil.extName(originalName);
            String date = DateUtil.today().replace("-", "");
            path = "upload/" + date + "/" + System.currentTimeMillis() + (StrUtil.isEmpty(suffix) ? "" : ("." + suffix));
            url = fileUrl + path;
            File targetFile = new File(filePositon + "/" + path);
            ByteArrayInputStream bis = null;
            try {
                byte[] bytes = null;
                if (com.castle.fortress.common.utils.FileUtils.isImage(file)) {
                    bytes = PicUtil.compressPic(file, 12, 350);
                } else {
                    bytes = file.getBytes();
                }
                bis = new ByteArrayInputStream(bytes);
                FileUtils.copyToFile(bis, targetFile);
                md5 = SecureUtil.md5(targetFile);
                List<FileTypeEnum> collect = Arrays.stream(FileTypeEnum.values()).collect(Collectors.toList());
                for (FileTypeEnum fileTypeEnum : collect) {
                    if (fileTypeEnum.getName().equals(suffix)) {
                        System.out.println("文件类型为：" + suffix);
                        String finalMd = md5;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                readFile(targetFile, finalMd);
                            }
                        }, "提取关键字").start();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                    }
                }
            }
            Map<String, Object> map = new HashMap<>(1);
            map.put("name", originalName);
            //页面访问路径
            map.put("url", url);
            //数据库存储路径
            map.put("path", path);
            map.put("md5", md5);
            rb.setData(map);
            rb.setSuccess(true);
            //阿里云存储
        } else {
            rb = ossService.putFile(file);
        }
        if (rb.isSuccess()) {
            Map<String, String> m = (Map<String, String>) rb.getData();
            SysOssRecordDto dto = new SysOssRecordDto();
            dto.setResourceName(m.get("name"));
            dto.setOssPlatform(configOssDto.getPlatform());
            dto.setResourceUrl(m.get("path"));
            sysOssRecordService.saveAsyn(dto);
        }
        return rb;

    }

    @Override
    public String getFilePathPrefix() {
        // 获取文件路径
        ConfigOssDto ftDto = new ConfigOssDto();
        ftDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigOssDto> ftDtoList = selectBySelective(ftDto);
        ConfigOssDto configOssDto = ftDtoList.get(0);
        OssPlatFormDto ossPlatFormDto = JSONUtil.toBean(configOssDto.getPtConfig(), OssPlatFormDto.class);
        String localFilePosition = ossPlatFormDto.getLocalFilePosition();
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            if (localFilePosition.endsWith("\\")) {
                return localFilePosition;
            } else {
                return localFilePosition + "\\";
            }
        } else {
            if (localFilePosition.endsWith("/")) {
                return localFilePosition;
            } else {
                return localFilePosition + "/";
            }
        }
    }

    @Async
    public void readFile(File file, String md5) {
        //执行耗时的逻辑
        List<String> keywords = null;
        try {
            long start = System.currentTimeMillis();
            FortressParseUtil fortressParseUtil = new FortressParseUtil();
            String filename = file.getPath();
            String contents = fortressParseUtil.parserFile(filename);
            log.debug("正文长度:" + contents.length());
            IJcsegService service = new JcsegServiceImpl(null);
            SegmenterConfig config = service.initConfig();
            keywords = new JcsegServiceImpl(null).keywordsExtractor(config, contents);
            log.debug(keywords.toString());
            log.debug("耗时:" + (System.currentTimeMillis() - start));
            redisUtils.set("keyword:" + md5, keywords, 3600000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
