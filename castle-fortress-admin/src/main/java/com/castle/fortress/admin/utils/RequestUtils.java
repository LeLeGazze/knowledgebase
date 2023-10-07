package com.castle.fortress.admin.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.core.wrapper.BodyReaderRequestWrapper;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求工具类
 * @author Dawn
 */
public class RequestUtils {

	public static Map<String,Object> getQueryParam(BodyReaderRequestWrapper requestWrapper){
		Map<String,Object> allParamsMap=getAllParams(requestWrapper);
		Map<String,Object> queryParamsMap=new HashMap<>();
		String queryString=requestWrapper.getQueryString();
		if(Strings.isNotEmpty(queryString)){
			String[] params=queryString.split("&");
			for(String param:params){
				String key=param.split("=")[0];
				if(allParamsMap.get(key)!=null){
					queryParamsMap.put(key,allParamsMap.get(key));
				}
			}
		}
		return queryParamsMap;
	}

	public static Map<String,Object> getBodyParam(BodyReaderRequestWrapper requestWrapper){
		Map<String,Object> bodyParamsMap=new HashMap<>();
		Map<String,Object> allParamsMap=getAllParams(requestWrapper);
		Map<String,Object> queryParamsMap=getQueryParam(requestWrapper);
		for(String key:allParamsMap.keySet()){
			bodyParamsMap.put(key,allParamsMap.get(key));
		}
		for(String key:queryParamsMap.keySet()){
			bodyParamsMap.remove(key);
		}
		return bodyParamsMap;
	}


	public static Map<String,Object> getAllParams(BodyReaderRequestWrapper requestWrapper){
		Map<String,Object> allParamsMap=new HashMap<>();
		Map<String , String[]> map = requestWrapper.getParameterMap();
		if(map != null){
			for(String key:map.keySet()){
				if(map.get(key)!=null && map.get(key).length>0){
					allParamsMap.put(key,map.get(key)[0]);
				}
			}
		}
		return allParamsMap;
	}

	/**
	 * 解密
	 * @param queryMap
	 * @param paramEncrptyKey 参数秘钥
	 * @return
	 */
	public static Map<String,Object>  decryptQueryMap(Map<String,Object> queryMap,String paramEncrptyKey){
		Map<String,Object> decryptMap = new HashMap<>();
		//密钥
		byte[] asekey = paramEncrptyKey.getBytes();
		//构建
		SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, asekey);
		for(String key:queryMap.keySet()){
			if(queryMap.get(key)!=null){
				decryptMap.put(key,aes.decryptStr(queryMap.get(key).toString(), CharsetUtil.CHARSET_UTF_8));
			}
		}
		return decryptMap;
	}

	/**
	 * 解密
	 * @param bodyParams
	 * @param paramEncrptyKey
	 * @return
	 */
	public static String decryptBodyParams(String bodyParams,String paramEncrptyKey){
		//密钥
		byte[] asekey = paramEncrptyKey.getBytes();
		//构建
		SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, asekey);
		Map<String,Object> bodyMap = JSONUtil.toBean(bodyParams, Map.class);
		Map<String,Object> decryptMap = new HashMap<>();
		for(String key:bodyMap.keySet()){
			if(bodyMap.get(key)!=null){
				decryptMap.put(key,aes.decryptStr(bodyMap.get(key).toString(), CharsetUtil.CHARSET_UTF_8));
			}
		}
		bodyParams= JSONUtil.toJsonStr(decryptMap);
		return bodyParams;
	}




}
