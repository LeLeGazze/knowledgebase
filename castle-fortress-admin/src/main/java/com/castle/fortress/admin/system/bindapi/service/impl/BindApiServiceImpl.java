package com.castle.fortress.admin.system.bindapi.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.system.bindapi.service.BindApiService;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.utils.BindApiUtils;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 绑定的第三方api服务类
 * @@author castle
 */
@Service
public class BindApiServiceImpl implements BindApiService {
    @Autowired
    private BindApiUtils bindApiUtils;

    @Override
    public String[] keywordsExtraction(String platformCode, String text, Long num) {
        if(StrUtil.isEmpty(text)){
            return new String[0];
        }
        if(num==null || num<0){
            num = 5L;
        }
        if(bindApiUtils.isOpen(platformCode) && bindApiUtils.isOpen("API_KEYWORDSEXTRACTION")){
            ConfigApiDto platform = bindApiUtils.getData(platformCode);
            ConfigApiDto apiDto = bindApiUtils.getData("API_KEYWORDSEXTRACTION");
            //腾讯
            if("PLAT_TENCENT".equals(platformCode)){
                String region = CommonUtil.verifyParamEmpty(apiDto.getParamMap().get("region"))?"ap-guangzhou":apiDto.getParamMap().get("region").toString();
                TencentBindApiClient tencentClient = new TencentBindApiClient(platform,region);
                return tencentClient.keywordsExtraction(platformCode,text,num);
            }
            return new String[0];
        }else{
            return new String[0];
        }

    }

    @Override
    public String autoSummarization(String platformCode, String text, Long Length) {
        if(StrUtil.isEmpty(text)){
            return "";
        }
        if(Length==null || Length<0){
            Length = 200L;
        }
        if(bindApiUtils.isOpen(platformCode) && bindApiUtils.isOpen("API_AUTOSUMMARIIZATION")){
            ConfigApiDto platform = bindApiUtils.getData(platformCode);
            ConfigApiDto apiDto = bindApiUtils.getData("API_AUTOSUMMARIIZATION");
            //腾讯
            if("PLAT_TENCENT".equals(platformCode)){
                String region = CommonUtil.verifyParamEmpty(apiDto.getParamMap().get("region"))?"ap-guangzhou":apiDto.getParamMap().get("region").toString();
                TencentBindApiClient tencentClient = new TencentBindApiClient(platform,region);
                return tencentClient.autoSummarization(platformCode,text,Length);
            }
            return "";
        }else{
            return "";
        }
    }

    @Override
    public Map<String, Object> textCorrection(String platformCode, String text) {
        Map<String,Object> map=new HashMap<>();
        if(StrUtil.isEmpty(text)){
            return map;
        }
        if(bindApiUtils.isOpen(platformCode) && bindApiUtils.isOpen("API_TEXTCORRECTION")){
            ConfigApiDto platform = bindApiUtils.getData(platformCode);
            ConfigApiDto apiDto = bindApiUtils.getData("API_TEXTCORRECTION");
            //腾讯
            if("PLAT_TENCENT".equals(platformCode)){
                String region = CommonUtil.verifyParamEmpty(apiDto.getParamMap().get("region"))?"ap-guangzhou":apiDto.getParamMap().get("region").toString();
                TencentBindApiClient tencentClient = new TencentBindApiClient(platform,region);
                return tencentClient.textCorrection(platformCode,text);
            }
            return map;
        }else{
            return map;
        }
    }

    @Override
    public Map<String, Object> textAudit(String platformCode, String text,String detectType) {
        Map<String,Object> map=new HashMap<>();
        if(StrUtil.isEmpty(text) || StrUtil.isEmpty(detectType)){
            return map;
        }
        if(bindApiUtils.isOpen(platformCode) && bindApiUtils.isOpen("API_TEXTAUDIT")){
            ConfigApiDto platform = bindApiUtils.getData(platformCode);
            ConfigApiDto apiDto = bindApiUtils.getData("API_TEXTAUDIT");
            //腾讯
            if("PLAT_TENCENT".equals(platformCode)){
                //腾讯的文本审核给予cos 需要绑定存储桶和地域
                if(CommonUtil.verifyParamEmpty(platform.getParamMap(),platform.getParamMap().get("bucketName"),platform.getParamMap().get("cosRegion"))){
                    throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
                }
                String region = platform.getParamMap().get("cosRegion").toString();
                TencentBindApiClient tencentClient = new TencentBindApiClient(platform,region);
                return tencentClient.textAudit(platformCode,text,detectType);
            //openapi
            }else if("PLAT_CASTLE".equals(platformCode)){
                OpenapiBindApiClient apiClient = new OpenapiBindApiClient(platform);
                return apiClient.textAudit(platformCode,text,detectType);
            }
            return map;
        }else{
            return map;
        }
    }

    @Override
    public String getAsrWs(String platformCode, String engineModelType, String voiceFormat) {
        String url="";
        if(StrUtil.isEmpty(engineModelType) || StrUtil.isEmpty(voiceFormat)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        if(bindApiUtils.isOpen(platformCode) && bindApiUtils.isOpen("API_ASRWSURL")){
            ConfigApiDto platform = bindApiUtils.getData(platformCode);
            ConfigApiDto apiDto = bindApiUtils.getData("API_ASRWSURL");
            //腾讯
            if("PLAT_TENCENT".equals(platformCode)){
                TencentBindApiClient tencentClient = new TencentBindApiClient(platform,"");
                return tencentClient.getAsrWs(platformCode,engineModelType,voiceFormat);
            }else{
                throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
            }
        }else{
            throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
        }
    }
}
