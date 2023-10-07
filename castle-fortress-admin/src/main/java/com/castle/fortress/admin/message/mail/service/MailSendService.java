package com.castle.fortress.admin.message.mail.service;

import com.castle.fortress.common.entity.RespBody;

import java.util.List;

/**
 * 邮件发送
 * @author castle
 */
public interface MailSendService {

    /**
     * 邮件发送
     * @param  mailCode 邮箱配置编码
     * @param subject 标题
     * @param body 邮件内容
     * @param toUser 接收人
     * @param toCCUser 抄送人
     * @param fileUrls 文件地址
     */
    RespBody mailSend(String mailCode,String subject, String body, String toUser, String toCCUser, List<String> fileUrls);
}
