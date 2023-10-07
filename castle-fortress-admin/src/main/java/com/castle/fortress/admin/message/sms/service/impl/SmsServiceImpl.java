package com.castle.fortress.admin.message.sms.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.message.sms.dto.ConfigSmsDto;
import com.castle.fortress.admin.message.sms.dto.SmsPlatFormDto;
import com.castle.fortress.admin.message.sms.service.ConfigSmsService;
import com.castle.fortress.admin.message.sms.service.SmsService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.enums.SmsPlatFormEnum;
import com.castle.fortress.common.enums.YesNoEnum;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private ConfigSmsService configSmsService;

    @Override
    public RespBody send(String smsCode, String mobile, String params) {
        if(StrUtil.isEmpty(smsCode)){
            throw new BizException(BizErrorCode.SMSCODE_ERROR);
        }
        ConfigSmsDto configSmsDto = new ConfigSmsDto();
        configSmsDto.setSmsCode(smsCode);
        configSmsDto.setStatus(YesNoEnum.YES.getCode());
        List<ConfigSmsDto> configSmsDtos = configSmsService.listConfigSms(configSmsDto);
        if(configSmsDtos == null || configSmsDtos.size()!=1){
            throw new BizException(BizErrorCode.SMSCODE_ERROR);
        }
        ConfigSmsDto config = configSmsDtos.get(0);
        SmsPlatFormDto platFormDto = JSONUtil.toBean(config.getSmsConfig(),SmsPlatFormDto.class);
        if(SmsPlatFormEnum.ALIYUN.getCode().equals(config.getPlatform())){
            AliyunSmsServiceImpl aliyunSmsService = new AliyunSmsServiceImpl(platFormDto);
            return aliyunSmsService.send(smsCode,mobile,params);
        }else if(SmsPlatFormEnum.TENCENTCLOUD.getCode().equals(config.getPlatform())){
            TencentcloudSmsServiceImpl tencentcloudSmsService = new TencentcloudSmsServiceImpl(platFormDto);
            return tencentcloudSmsService.send(smsCode,mobile,params);
        }
        return RespBody.data("发送成功");
    }

}
