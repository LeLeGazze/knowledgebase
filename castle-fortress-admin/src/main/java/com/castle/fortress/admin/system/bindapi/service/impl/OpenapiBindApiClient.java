package com.castle.fortress.admin.system.bindapi.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.system.bindapi.service.BindApiService;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.utils.SignUtils;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import okhttp3.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 绑定的openapi服务类
 */
public class OpenapiBindApiClient implements BindApiService {
    private String secretId;
    private String secretKey;
    private String endpoint = "http://api.icuapi.com/openapi";

    public OpenapiBindApiClient(ConfigApiDto platform){
        this.secretId = platform.getParamMap().get("secretId").toString();
        this.secretKey = platform.getParamMap().get("secretKey").toString();

    }
    @Override
    public String[] keywordsExtraction(String platformCode, String text, Long num) {
        return new String[0];
    }

    @Override
    public String autoSummarization(String platformCode, String text, Long Length) {
        return null;
    }

    @Override
    public Map<String, Object> textCorrection(String platformCode, String text) {
        return null;
    }

    @Override
    public Map<String, Object> textAudit(String platformCode, String text,String detectType) {
        Map<String, Object> map = new HashMap<>();
        Map<String, String> queryParams=new HashMap<>();
        queryParams.put("Content",text);
        queryParams.put("DetectType",detectType);
        try {
            String result = doRequest("get","/safe/textAudit",queryParams,null);
            if(StrUtil.isNotEmpty(result)){
                JSONObject o = new JSONObject(result);
                if(GlobalRespCode.SUCCESS.getCode() == o.getInt("code")){
                    map = JSONUtil.toBean(o.getJSONObject("data"),Map.class);
                }else{
                    throw new BizException(o.getStr("msg"));
                }
            }
        } catch (IOException e) {
            throw new BizException(e.getMessage());
        }
        return map;
    }

    /**
     * 发起请求
     * @param method 默认是get
     * @param url 请求路径 以/开头
     * @param queryParams
     * @param bodyMap
     * @return
     */
    private String doRequest(String method,String url,Map<String, String> queryParams,Map<String, Object> bodyMap) throws IOException {
        if(StrUtil.isEmpty(url)){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        String datetime = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
        // 请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("C-Date", datetime);
        //支持 application/json 与 multipart/form-data
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("C-Secret",this.secretId);
        Map<String,Object> signMap=new HashMap<>();
        if(queryParams!=null){
            signMap.putAll(queryParams);
        }
        if(bodyMap!=null){
            signMap.putAll(bodyMap);
        }
        // 签名
        String sign = SignUtils.calcSign(this.secretId, this.secretKey, datetime,signMap);
        headers.put("Sign",sign);

        // 接口的url地址
        StringBuilder requestUrl = new StringBuilder(this.endpoint+url);

        //拼接queryParam
        if(!queryParams.isEmpty()){
            requestUrl.append("?");
            for(String paramKey:queryParams.keySet()){
                requestUrl.append(paramKey+"="+queryParams.get(paramKey)+"&");
            }
        }
        //发送请求
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder=new Request.Builder().url(requestUrl.toString());
        for(String headerKey:headers.keySet()){
            requestBuilder.addHeader(headerKey,headers.get(headerKey));
        }
        if(StrUtil.isEmpty(method)){
            method = "get";
        }
        //get请求
        if("get".equals(method.toLowerCase(Locale.ROOT))){
            Request request=requestBuilder.build();
            final Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        //post请求
        }else if("post".equals(method.toLowerCase(Locale.ROOT))){
            for(String key:bodyMap.keySet()){
                bodyMap.put(key,bodyMap.get(key).toString());
            }
            String body= JSONUtil.toJsonStr(bodyMap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), body);
            Request request = requestBuilder.post(requestBody).build();
            final Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        }else{
            throw new BizException(BizErrorCode.REQUEST_METHOD_ERROR);
        }
    }


}
