package com.castle.fortress.admin.utils;

import cn.hutool.json.JSONUtil;
import okhttp3.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;


/**
 * 请求工具类
 */
public class HttpUtil {

		public static String doRequest(String method,String url,Map<String, String> headers,Map<String, String> queryParams,Map<String, Object> bodyMap){
				// 接口的url地址
				StringBuilder u = new StringBuilder(url);
				//拼接queryParam
				if(queryParams!=null){
						if(!queryParams.isEmpty()){
								u.append("?");
								for(String paramKey:queryParams.keySet()){
										u.append(paramKey+"="+queryParams.get(paramKey)+"&");
								}
						}
				}
				String responseData = "";
				//发送请求
				OkHttpClient okHttpClient = new OkHttpClient();
				Request.Builder requestBuilder=new Request.Builder().url(u.toString());
				for(String headerKey:headers.keySet()){
						requestBuilder.addHeader(headerKey,headers.get(headerKey));
				}
				if("get".equals(method.toLowerCase(Locale.ROOT))){
						Request request=requestBuilder.build();
						try {
								final Call call = okHttpClient.newCall(request);
								Response response = call.execute();
								responseData = response.body().string();
						} catch (IOException e) {
								e.printStackTrace();
						}
				}else if("post".equals(method.toLowerCase(Locale.ROOT))){
						String body=bodyMap!=null?JSONUtil.toJsonStr(bodyMap):"";
						RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), body);
						Request request = requestBuilder.post(requestBody).build();
						try {
								final Call call = okHttpClient.newCall(request);
								Response response = call.execute();
								responseData = response.body().string();
						} catch (IOException e) {
								e.printStackTrace();
						}
				}
//				System.out.println("请求结果："+responseData);
				return responseData;
		}

}

