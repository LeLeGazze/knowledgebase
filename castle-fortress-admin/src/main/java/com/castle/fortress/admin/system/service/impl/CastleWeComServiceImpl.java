package com.castle.fortress.admin.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.service.CastleWeComService;
import com.castle.fortress.admin.system.service.ConfigApiService;
import com.castle.fortress.common.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 框架绑定api配置管理 服务实现类
 *
 * @author castle
 * @since 2022-04-12
 */
@Service
public class CastleWeComServiceImpl implements CastleWeComService {

    @Autowired
    private ConfigApiService configApiService;


    public String getToken(){
        ConfigApiDto configApiDto = configApiService.getByBindCode("SELF_WECOM");
        if (configApiDto== null || StrUtil.isEmpty(configApiDto.getBindDetail())){
            throw new BizException("未配置企微相关参数");
        }
        JSONObject jsonObject = JSONUtil.parseObj(configApiDto.getBindDetail());
        String clientId = jsonObject.getStr("clientId");
        String agentSecret = jsonObject.getStr("agentSecret");

        // 获取token
//        String clientId = "ww8b0b25c191b6ee3c";// 企业微信企业id
//        String agentId = ""; // 企微应用id
//        String agentSecret = "nUM5kRjJl-4Yb51c1Xw9ozGFSPRpPCrwytmaluHTamE"; //  企微应用secret
        if(StrUtil.isBlank(clientId)  || StrUtil.isBlank(agentSecret)){
            throw new BizException("含有未配置的企微参数");
        }
        //获取 access_token
        String resultToken = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid="+clientId +
                "&corpsecret="+agentSecret).execute().body();
        System.err.println("获取access_token接口返回的信息:" + resultToken);
        JSONObject resultTokenJson= JSONUtil.parseObj(resultToken);
        String resultTokenCode = resultTokenJson.getStr("errcode");
        if(!"0".equals(resultTokenCode)){
            throw new BizException("操作失败,请检查企微参数配置是否正确");
        }
        String accessToken = resultTokenJson.getStr("access_token");
        String tokenExpires =  resultTokenJson.getStr("expires_in");
        System.err.println("获取到的access_token:" + accessToken);
        // 保存至配置表中
//        cpbWechatworkConfigDTO.setAccessToken(accessToken);
//        cpbWechatworkConfigDTO.setTokenExpires(tokenExpires);
//        cpbWechatworkConfigDTO.setTokenUpdateDate(new Date());
//        cpbWechatworkConfigService.update(cpbWechatworkConfigDTO);
        return accessToken;
    }

    /**
     * 根据手机号获取企微userid
     * @param mobile
     */
    public String getUserIdByMobile(String mobile){
        return getUserIdByMobile(mobile , getToken());
    }

    /**
     * 根据手机号获取企微userid
     * @param mobile
     */
    @Override
    public String getUserIdByMobile(String mobile, String token) {
        if(StrUtil.isBlank(mobile) ){
            throw new BizException("参数错误");
        }
        JSONObject jsonObject = JSONUtil.createObj().set("mobile",mobile);
        //获取 userid
        String resultToken = HttpRequest.post("https://qyapi.weixin.qq.com/cgi-bin/user/getuserid?access_token="+getToken()).body(jsonObject.toString()).execute().body();
        System.err.println("手机号获取userid接口返回的信息:" + resultToken);
        JSONObject resultTokenJson= JSONUtil.parseObj(resultToken);

        String userid =  resultTokenJson.getStr("userid");
        return userid;
    }



    /**
     * 根据企微userid获取用户信息
     * @param userId
     */
    public JSONObject getUserInfoByUserId(String userId){
       return getUserInfoByUserId(userId , getToken());
    }
    @Override
    public JSONObject getUserInfoByUserId(String userId, String token) {
        if(StrUtil.isBlank(userId) ){
            throw new BizException("参数错误");
        }
        //获取 userid
        String resultToken = HttpRequest.get("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token="+getToken()+"&userid="+ userId).execute().body();
        System.err.println("userid获取用户信息接口返回的信息:" + resultToken);
        JSONObject resultTokenJson= JSONUtil.parseObj(resultToken);
        return resultTokenJson;
    }

    @Override
    public JSONObject getUserInfoByMobile(String mobile) {
        return getUserInfoByMobile(mobile , getToken());
    }

    @Override
    public JSONObject getUserInfoByMobile(String mobile, String token) {
        return getUserInfoByUserId(getUserIdByMobile(mobile , token) , token);
    }

    //    public static void main(String[] args) {
//        System.err.println(getUserInfoByUserId(getUserIdByMobile("15966198621")));
//    }

}

