package com.castle.fortress.admin.message.mail.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.admin.message.mail.entity.CastleConfigMailEntity;
import com.castle.fortress.admin.message.mail.entity.CastleMessageEmailRecordEntity;
import com.castle.fortress.admin.message.mail.service.CastleConfigMailService;
import com.castle.fortress.admin.message.mail.service.CastleMessageEmailRecordService;
import com.castle.fortress.admin.message.mail.service.MailSendService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeUtility;
import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class MailSendServiceImpl implements MailSendService {
    @Autowired
    private CastleConfigMailService castleConfigMailService;

    @Autowired
    private CastleMessageEmailRecordService castleMessageEmailRecordService;

    @Value("${castle.mailFilePath.mailFile}")
    private String filePath;

    @Override
    public RespBody mailSend(String mailCode,String subject, String body, String toUser, String toCCUser,List<String> fileUrls) {
        // 获取配置信息 //  只获取启用的
        QueryWrapper<CastleConfigMailEntity> wrapper= new QueryWrapper();
        wrapper.eq("code",mailCode);
        List<CastleConfigMailEntity> list = castleConfigMailService.list(wrapper);
        if(list==null || list.size()!= 1){
            throw new BizException(BizErrorCode.MAILCODE_NO_EXIST_ERROR);
        }
        CastleConfigMailEntity config = list.get(0);
//        // 发送带附件邮件
        HtmlEmail email = new HtmlEmail();
        // 获取文件名
        String fileName = null;
        try {
            // smtp host
            email.setHostName(config.getSmtp());
            //端口
            email.setSmtpPort(config.getPort());
            //开启SSL链接
            email.setSSLOnConnect(true);
            // 登陆邮件服务器的用户名和密码 smtp
            email.setAuthentication ( config.getMail(), config.getPassword());
            // 发送人邮箱，昵称
            email.setFrom (config.getMail(), config.getNickName() );
            //指定字符集格式
            email.setCharset("UTF-8");
            // 标题
            email.setSubject (subject);
            // 邮件内容
            email.setMsg(StrUtil.isEmpty(body)?"&nbsp;":body);
            // 将接受附件保存到本地
            for(String fUrl:fileUrls){
                if (fUrl != null) {
                    // 获取文件名
                    fileName = FileUtil.file(fUrl).getName();
                }
                // 1.将文件转换为input流
                if (fileName != null) {
                    URL url = new URL(fUrl);
                    InputStream ins = url.openStream();
                    // 3.将文件保存在本地
                    saveFile(ins,fileName);
                    // 获取本地保存的文件
                    EmailAttachment attachment = new EmailAttachment();
                    //指定附件在本地的绝对路径
                    attachment.setPath(filePath + fileName);
                    // 也可以以网络的方式
                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
                    // 附件描述
                    assert fileName != null;
                    attachment.setName(MimeUtility.encodeText(fileName));
                    //添加附件
                    email.attach(attachment);
                }
            }
            // 将接受人字符串更改为数组
            if (toUser.contains(";")) {
                String[] to = toUser.split(";");
                // 接收人
                email.addTo(to);
            } else {
                email.addTo(toUser);
            }
            // 将抄送人字符串更改为数组
            if (StrUtil.isNotBlank(toCCUser)) {
                if (toCCUser.contains(";")) {
                    String[] cc = toCCUser.split(";");
                    email.addCc(cc);
                } else {
                    email.addCc(toCCUser);
                }
            }
            // 发送
            email.send();

            // 新建一条邮件记录实体

            CastleMessageEmailRecordEntity emailRecordEntity = new CastleMessageEmailRecordEntity();
            // 补全发送人
            emailRecordEntity.setSender(config.getMail());
            // 补全发送时间
            emailRecordEntity.setSendTime(new Date());
            // 补全发送信息标题
            emailRecordEntity.setEmailTitle(subject);
            // 补全发送邮件内容
            emailRecordEntity.setEmailBody(body);
            //接收人
            emailRecordEntity.setToUser(toUser);
            //抄送人
            emailRecordEntity.setToCuser(toCCUser);
            // 发送完成后在邮件记录表中新增一条记录数据沉淀
            castleMessageEmailRecordService.save(emailRecordEntity);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            for(String fUrl:fileUrls){
                if (fUrl != null) {
                    // 获取文件名
                    fileName = FileUtil.file(fUrl).getName();
                }
                // 删除这个文件....
                if (fileName != null) {
                    deleteFile(filePath + fileName);
                }
            }
        }
        return RespBody.status(true);
    }

    /**
     * 将文件保存在本地
     * @param inputStream
     * @param fileName
     */
    private void saveFile(InputStream inputStream, String fileName) {
        OutputStream os = null;
        try {
            String path = filePath;
            // 2、保存到临时文件
            // 10K的数据缓冲
            byte[] bs = new byte[10240];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除单个文件
     * @param   Path    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public Boolean deleteFile(String Path) {
        Boolean flag = false;
        File file = new File(Path);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


}
