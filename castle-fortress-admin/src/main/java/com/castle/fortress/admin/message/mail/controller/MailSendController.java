package com.castle.fortress.admin.message.mail.controller;

import cn.hutool.core.io.FileUtil;
import com.castle.fortress.admin.message.mail.service.MailSendService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.castle.fortress.common.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 邮件发送 控制器
 *
 * @author castle
 * @since 2021-04-12
 */
@Api(tags="邮件发送管理控制器")
@RestController
public class MailSendController {
    @Autowired
    private MailSendService mailSendService;

    /**
     * 邮件发送  有附件版/附件必填
     * @param params
     * @return
     */
    @PostMapping("/message/mail/mailFileSend")
    @ApiOperation(value = "邮件发送带附件", notes = "")
    public RespBody mailSend(@RequestBody Map<String, Object> params) throws UnsupportedEncodingException {

        if(CommonUtil.verifyParamNull(params.get("code"),params.get("subject"),params.get("toUser"))){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        String code = CommonUtil.emptyIfNull(params.get("code")) ;
        String subject = CommonUtil.emptyIfNull(params.get("subject")) ;
        String body = CommonUtil.emptyIfNull(params.get("body")) ;
        String toUser = CommonUtil.emptyIfNull(params.get("toUser")) ;
        String toCCUser = CommonUtil.emptyIfNull(params.get("toCCUser")) ;
        List<String> fileUrl = params.get("file")==null?new ArrayList():(ArrayList<String>)params.get("file");
        return mailSendService.mailSend(code,subject,body,toUser,toCCUser,fileUrl);
    }


}
