package com.castle.fortress.admin.message.sms.service;

import com.castle.fortress.common.entity.RespBody;

/**
 * 短信服务类
 *
 * @author castle
 * @since 2021-04-12
 */
public interface SmsService {

    /**
     * 发送短信
     * @param smsCode   短信编码
     * @param mobile   手机号
     * @param params   短信参数
     */
    RespBody send(String smsCode, String mobile, String params);

}
