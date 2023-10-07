package com.castle.fortress.admin.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.castle.fortress.admin.system.dto.ConfigApiDto;
import com.castle.fortress.admin.system.service.ConfigApiService;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用openApi接口的公共方法
 */
@Component
public class OpenApiUtil {
	//openapi域名
	private static final String DOMAIN = "http://api.icuapi.com/openapi";
	@Autowired
	private ConfigApiService configApiService;

	/**
	 * 获取调用秘钥
	 * @return
	 */
	public Map<String,String> getInvokeKeys(){
		Map<String,String> map = new HashMap<>();
		ConfigApiDto configApiDto = new ConfigApiDto();
		configApiDto.setBindCode("PLAT_CASTLE");
		configApiDto.setGroupCode("00");
		List<ConfigApiDto> list =  configApiService.listConfigApi(configApiDto);
		if(list==null || list.size()!=1 || StrUtil.isEmpty(list.get(0).getBindDetail())){
			throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
		}
		try {
			JSONObject o = new JSONObject(list.get(0).getBindDetail());
			if(StrUtil.isNotEmpty(o.getStr("secretId"))&&StrUtil.isNotEmpty(o.getStr("secretKey"))){
				map.put("secretId",o.getStr("secretId"));
				map.put("secretKey",o.getStr("secretKey"));
			}else{
				throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
			}
		}catch (Exception e){
			throw new BizException(BizErrorCode.BIND_PLATFORM_ERROR);
		}
		return map;
	}

	/**
	 * get请求
	 * @param url
	 * @param queryParams
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String doGet(String url, Map<String, String> queryParams) throws UnsupportedEncodingException {
		Map<String,String> map = getInvokeKeys();
		String SECRET_ID = map.get("secretId");
		String SECRET_KEY = map.get("secretKey");
		String datetime = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);

		// 请求头
		Map<String, String> headers = new HashMap<>();
		headers.put("C-Date", datetime);
		//支持 application/json 与 multipart/form-data
		headers.put("Content-Type", "application/json;charset=utf-8");
		headers.put("C-Secret",SECRET_ID);

		Map<String,Object> signMap=new HashMap<>();
		if(queryParams!=null){
			signMap.putAll(queryParams);
		}

		// 签名
		String sign = SignUtils.calcSign(SECRET_ID, SECRET_KEY, datetime,signMap);
		headers.put("Sign",sign);

		return HttpUtil.doRequest("get",DOMAIN+url,headers,queryParams,null);
	}

	/**
	 * post请求
	 * @param url
	 * @param queryParams
	 * @param bodyMap
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String doPost(String url, Map<String, String> queryParams,Map<String, Object> bodyMap) throws UnsupportedEncodingException {
		Map<String,String> map = getInvokeKeys();
		String SECRET_ID = map.get("secretId");
		String SECRET_KEY = map.get("secretKey");
		String datetime = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN);

		// 请求头
		Map<String, String> headers = new HashMap<>();
		headers.put("C-Date", datetime);
		//支持 application/json 与 multipart/form-data
		headers.put("Content-Type", "application/json;charset=utf-8");
		headers.put("C-Secret",SECRET_ID);

		Map<String,Object> signMap=new HashMap<>();
		if(queryParams!=null){
			signMap.putAll(queryParams);
		}
		if(bodyMap!=null){
			signMap.putAll(bodyMap);
		}

		// 签名
		String sign = SignUtils.calcSign(SECRET_ID, SECRET_KEY, datetime,signMap);
		headers.put("Sign",sign);

		return HttpUtil.doRequest("post",DOMAIN+url,headers,queryParams,bodyMap);
	}
}
