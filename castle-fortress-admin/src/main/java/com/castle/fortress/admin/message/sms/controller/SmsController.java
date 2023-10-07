package com.castle.fortress.admin.message.sms.controller;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.message.sms.service.SmsService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 消息发送 控制器
 *
 * @author castle
 * @since 2021-04-12
 */
@Api(tags="短信配置表管理控制器")
@RestController
public class SmsController {
    @Autowired
    private SmsService smsService;

    @PostMapping("/message/sms/send")
    @ApiOperation("发送短信")
    public RespBody send(@RequestBody Map<String,String> map){
        if(map == null || StrUtil.isEmpty(map.get("smsCode")) || StrUtil.isEmpty(map.get("mobile"))){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        return smsService.send(map.get("smsCode"), map.get("mobile"), map.get("params"));
    }


}
