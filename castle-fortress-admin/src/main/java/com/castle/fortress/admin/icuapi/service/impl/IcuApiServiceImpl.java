package com.castle.fortress.admin.icuapi.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.icuapi.service.IcuApiService;
import com.castle.fortress.admin.utils.OpenApiUtil;
import com.castle.fortress.admin.utils.SignUtils;
import com.castle.fortress.common.entity.RespBody;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Castle
 */
@Service
public class IcuApiServiceImpl implements IcuApiService {
    @Autowired
    private OpenApiUtil openApiUtil;
    /**
     * icuApi
     */

    // icuApi 接口域名
    public static final String ICU_API_URL= "http://api.icuapi.com/openapi";
    
    
    @Override
    public RespBody<JSONObject> ipToAddress(String ip)  {
        Map<String , String> params = new HashMap<>();
        params.put("ip" , ip);
        return getApi(params,"/lbs/ipToAddress");
    }

    @Override
    public RespBody<JSONObject> idCardVerification(String name, String idCard ) {
        Map<String , String> params = new HashMap<>();
        params.put("IdCard" , idCard);
        params.put("Name" , name);

        return getApi( params,"/safe/idCardVerification");
    }

    @Override
    public RespBody<JSONObject> idCardOcr(String frontImageBase64, String frontImageUrl, String backImageBase64,String backImageUrl) {
        Map<String , String> params = new HashMap<>();
        params.put("FrontImageBase64" , frontImageBase64);
        params.put("FrontImageUrl" , frontImageUrl);
        params.put("BackImageBase64" , backImageBase64);
        params.put("BackImageUrl" , backImageUrl);
        return getApi( params,"/ocr/idCardDiscern");
    }


    public RespBody<JSONObject> getApi(Map<String , String> queryParams , String path ){
        Map<String,String> map = openApiUtil.getInvokeKeys();
        String ICU_API_ID = map.get("secretId");
        String ICU_API_KEY = map.get("secretKey");
        // 请求日期减40秒
        Date date = new Date(System.currentTimeMillis() - (1000 * 40));

        String datetime = DateUtil.format(date, DatePattern.NORM_DATETIME_PATTERN);
        System.out.println(datetime);

        // 请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("C-Date", datetime);
        //支持 application/json 与 multipart/form-data
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("C-Secret", ICU_API_ID);

        // 查询参数 根据实际情况调整
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("ip", ip);


        Map<String, Object> signMap = new HashMap<>();
        signMap.putAll(queryParams);

        // 签名
        String sign = null;
        try {
            sign = SignUtils.calcSign(ICU_API_ID, ICU_API_KEY, datetime, signMap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.put("Sign", sign);
        System.out.println(sign);

        // 接口的url地址
        StringBuilder url = new StringBuilder(ICU_API_URL + path);

        //拼接queryParam
        if (!queryParams.isEmpty()) {
            url.append("?");
            for (String paramKey : queryParams.keySet()) {
                url.append(paramKey + "=" + queryParams.get(paramKey) + "&");
            }
        }
        //发送请求
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder().url(url.toString());
        for (String headerKey : headers.keySet()) {
            requestBuilder.addHeader(headerKey, headers.get(headerKey));
        }
        Request requestData = requestBuilder.build();
        final Call call = okHttpClient.newCall(requestData);
        Response response = null;
        String responseData = null;
        try {
            response = call.execute();
            responseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(responseData);
        JSONObject json = JSONUtil.parseObj(responseData);
        if ("0".equals(json.getStr("code"))){
            return RespBody.data(json.getJSONObject("data"));
        }else {
            return RespBody.fail(json.getStr("msg"));
        }
//			JSONObject jo =new JSONObject(responseData);
    }

    public RespBody<JSONObject> postApi(Map<String , String> queryParams ,Map<String , String> bodyMap , String path ){
        Map<String,String> map = openApiUtil.getInvokeKeys();
        String ICU_API_ID = map.get("secretId");
        String ICU_API_KEY = map.get("secretKey");
        String datetime = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);
        System.out.println(datetime);

        // 请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("C-Date", datetime);
        //支持 application/json 与 multipart/form-data
        headers.put("Content-Type", "application/json;charset=utf-8");
        headers.put("C-Secret",ICU_API_ID);

        // 查询参数 根据实际情况调整
//        Map<String, String> queryParams = new HashMap<>();
//        queryParams.put("参数名","参数值");

        // body参数 根据实际情况调整
//        Map<String, Object> bodyMap = new HashMap<>();
//        bodyMap.put("参数名","参数值");

        Map<String,Object> signMap=new HashMap<>();
        signMap.putAll(queryParams);
        signMap.putAll(bodyMap);

        // 签名
        String sign = null;
        try {
            sign = SignUtils.calcSign(ICU_API_ID, ICU_API_KEY, datetime,signMap);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return RespBody.fail("接口异常");
        }
        headers.put("Sign",sign);

        // 接口的url地址
        StringBuilder url = new StringBuilder(ICU_API_URL + path);

        //拼接queryParam
        if(!queryParams.isEmpty()){
            url.append("?");
            for(String paramKey:queryParams.keySet()){
                url.append(paramKey+"="+queryParams.get(paramKey)+"&");
            }
        }
        //发送请求
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder requestBuilder=new Request.Builder().url(url.toString());
        for(String headerKey:headers.keySet()){
            requestBuilder.addHeader(headerKey,headers.get(headerKey));
        }
        for(String key:bodyMap.keySet()){
            bodyMap.put(key,bodyMap.get(key).toString());
        }
        String body= JSONUtil.toJsonStr(bodyMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), body);
        Request request = requestBuilder.post(requestBody).build();
        final Call call = okHttpClient.newCall(request);
        Response response = null;
        String responseData = null;
        try {
            response = call.execute();
            responseData = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return RespBody.fail("接口异常");
        }
        System.out.println(responseData);
        JSONObject json = JSONUtil.parseObj(responseData);
        if ("0".equals(json.getStr("code"))){
            return RespBody.data(json.getJSONObject("data"));
        }else {
            return RespBody.fail(json.getStr("msg"));
        }
    }

}
