package com.castle.fortress.admin.message.sms.service.impl;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.message.sms.dto.SmsPlatFormDto;
import com.castle.fortress.admin.message.sms.service.SmsService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;

/**
 * 腾讯云短信发送实现类
 * @author castle
 */
public class TencentcloudSmsServiceImpl implements SmsService {
    private SmsPlatFormDto platformConfig;
    private SmsClient client;

    public TencentcloudSmsServiceImpl(SmsPlatFormDto platformConfig) {
        this.platformConfig = platformConfig;
        Credential cred = new Credential(this.platformConfig.getTencentcloudSecretId(), this.platformConfig.getTencentcloudSecretKey());
        this.client = new SmsClient(cred, this.platformConfig.getTencentcloudRegion()!=null?this.platformConfig.getTencentcloudRegion():"");
    }

    @Override
    public RespBody send(String smsCode, String mobile, String params) {
        /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
         * 你可以直接查询SDK源码确定接口有哪些属性可以设置
         * 属性可能是基本类型，也可能引用了另一个数据结构
         * 推荐使用IDE进行开发，可以方便的跳转查阅各个接口和数据结构的文档说明 */
       try { SendSmsRequest req = new SendSmsRequest();

        /* 填充请求参数,这里request对象的成员变量即对应接口的入参
         * 你可以通过官网接口文档或跳转到request对象的定义处查看请求参数的定义
         * 基本类型的设置:
         * 帮助链接：
         * 短信控制台: https://console.cloud.tencent.com/sms/smslist
         * sms helper: https://cloud.tencent.com/document/product/382/3773 */

        /* 短信应用ID: 短信SdkAppid在 [短信控制台] 添加应用后生成的实际SdkAppid，示例如1400006666 */
        req.setSmsSdkAppid(this.platformConfig.getTencentcloudSdkAppId());

        /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，签名信息可登录 [短信控制台] 查看 */
        req.setSign(this.platformConfig.getTencentcloudSign());

        /* 模板 ID: 必须填写已审核通过的模板 ID。模板ID可登录 [短信控制台] 查看 */
        req.setTemplateID(this.platformConfig.getTencentcloudTemplateId());

        /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
         * 示例如：+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
        String[] phoneNumbers = {"+86"+mobile};
        req.setPhoneNumberSet(phoneNumbers);

        /* 模板参数: 若无模板参数，则设置为空*/
        String[] templateParams = StrUtil.isEmpty(params) ? new String[]{}:params.split(",");
        req.setTemplateParamSet(templateParams);

        /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
         * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
        SendSmsResponse res = this.client.SendSms(req);
        if(res.getSendStatusSet().length>0){
            SendStatus sendStatus = res.getSendStatusSet()[0];
            if("Ok".equals(sendStatus.getCode())){
                return RespBody.data("发送成功");
            }else{
                return RespBody.fail(BizErrorCode.SMS_SEND_ERROR.getCode(),sendStatus.getMessage());
            }
        }else{
            return RespBody.fail(BizErrorCode.SMS_SEND_ERROR);
        }
    } catch (TencentCloudSDKException e) {
        e.printStackTrace();
           return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
    }
    }

    public static void main(String[] args) {

    }

}
