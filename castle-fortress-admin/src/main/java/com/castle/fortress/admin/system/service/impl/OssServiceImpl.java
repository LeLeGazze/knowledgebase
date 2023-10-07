package com.castle.fortress.admin.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.system.dto.ConfigOssDto;
import com.castle.fortress.admin.system.dto.OssPlatFormDto;
import com.castle.fortress.admin.system.entity.ConfigOssEntity;
import com.castle.fortress.admin.system.mapper.ConfigOssMapper;
import com.castle.fortress.admin.system.service.OssService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.OssEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.utils.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * oss 服务实现类
 * @author castle
 */
@Service
public class OssServiceImpl implements OssService {
    @Autowired
    private ConfigOssMapper configOssMapper;
    @Override
    public RespBody putFile(byte[] content,String fileName) {

        ConfigOssDto ftDto = queryConfigFt();
        //阿里云oss
        if(OssEnum.ALIYUN.getCode().equals(ftDto.getPlatform())){
            OssAliyunServiceImpl aliyunService = new OssAliyunServiceImpl(ftDto.getOssPlatFormDto());
            return aliyunService.putFile(content,fileName);
        }
        return RespBody.fail(BizErrorCode.FT_CONFIG_ERROR);
    }

    @Override
    public RespBody putFile(File file) {
        ConfigOssDto ftDto = queryConfigFt();
        //阿里云oss
        if(OssEnum.ALIYUN.getCode().equals(ftDto.getPlatform())){
            OssAliyunServiceImpl aliyunService = new OssAliyunServiceImpl(ftDto.getOssPlatFormDto());
            return aliyunService.putFile(file);
        }
        return RespBody.fail(BizErrorCode.FT_CONFIG_ERROR);
    }

    @Override
    public RespBody putFile(MultipartFile file) {
        ConfigOssDto ftDto = queryConfigFt();
        //阿里云oss
        if(OssEnum.ALIYUN.getCode().equals(ftDto.getPlatform())){
            OssAliyunServiceImpl aliyunService = new OssAliyunServiceImpl(ftDto.getOssPlatFormDto());
            return aliyunService.putFile(file);
        //京东云oss
        }else if(OssEnum.JDCLOUD.getCode().equals(ftDto.getPlatform())){
            OssJdcloudServiceImpl jdcloudService = new OssJdcloudServiceImpl(ftDto.getOssPlatFormDto());
            return jdcloudService.putFile(file);
        //腾讯云
        }else if(OssEnum.TENCENT_COS.getCode().equals(ftDto.getPlatform())){
            OssTencentCloudServiceImpl tencentCloudService = new OssTencentCloudServiceImpl(ftDto.getOssPlatFormDto());
            return tencentCloudService.putFile(file);
        }
        return RespBody.fail(BizErrorCode.FT_CONFIG_ERROR);
    }

    /**
     * 查询生效的存储配置
     * @return
     */
    private ConfigOssDto queryConfigFt(){
        List<ConfigOssEntity> list = configOssMapper.selectStatusYes();
        if(list == null || list.size()!= 1){
            throw new BizException(BizErrorCode.FT_CONFIG_ERROR);
        }
        ConfigOssEntity ftEntity = list.get(0);
        ConfigOssDto ftDto= ConvertUtil.transformObj(ftEntity, ConfigOssDto.class);
        OssPlatFormDto platFormDto = JSONUtil.toBean(ftEntity.getPtConfig(), OssPlatFormDto.class);
        if(platFormDto == null){
            throw new BizException(BizErrorCode.FT_CONFIG_ERROR);
        }
        ftDto.setOssPlatFormDto(platFormDto);
        return ftDto;
    }
}
