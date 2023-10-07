package com.castle.fortress.admin.message.sms.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.castle.fortress.admin.message.sms.dto.SmsPlatFormDto;
import com.castle.fortress.admin.message.sms.service.SmsService;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;

/**
 * 阿里云短信发送实现类
 * @author castle
 */
public class AliyunSmsServiceImpl implements SmsService {
    private SmsPlatFormDto platformConfig;
    private IAcsClient client ;

    public AliyunSmsServiceImpl(SmsPlatFormDto platformConfig) {
        this.platformConfig = platformConfig;
        if(StrUtil.isEmpty(platformConfig.getAliyunRegionId())){
            this.platformConfig.setAliyunRegionId("cn-hangzhou");
        }
        if(StrUtil.isEmpty(platformConfig.getAliyunEndpoint())){
            this.platformConfig.setAliyunEndpoint("dysmsapi.aliyuncs.com");
        }
        DefaultProfile profile = DefaultProfile.getProfile(this.platformConfig.getAliyunRegionId(), this.platformConfig.getAliyunAccessKeyId(), this.platformConfig.getAliyunAccessKeySecret());
        this.client = new DefaultAcsClient(profile);
    }

    @Override
    public RespBody send(String smsCode, String mobile, String params) {
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.setSysDomain(this.platformConfig.getAliyunEndpoint());
        request.putQueryParameter("RegionId", this.platformConfig.getAliyunRegionId());
        request.putQueryParameter("PhoneNumbers", mobile);
        request.putQueryParameter("SignName", this.platformConfig.getAliyunSignName());
        request.putQueryParameter("TemplateCode", this.platformConfig.getAliyunTemplateCode());
        request.putQueryParameter("TemplateParam", params);
        try {
            CommonResponse response = client.getCommonResponse(request);
            JSONObject json = JSONUtil.parseObj(response.getData());
            if(json == null){
                return RespBody.fail(BizErrorCode.SMS_SEND_ERROR);
            }
            if("OK".equals(json.get("Code"))){
                return RespBody.data("发送成功");
            }else{
                return RespBody.fail(BizErrorCode.SMS_SEND_ERROR.getCode(),json.getStr("Message"));
            }
        } catch (ServerException e) {
            e.printStackTrace();
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        } catch (ClientException e) {
            e.printStackTrace();
            return RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
    }

}
