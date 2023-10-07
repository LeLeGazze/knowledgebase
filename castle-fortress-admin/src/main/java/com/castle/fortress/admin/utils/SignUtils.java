package com.castle.fortress.admin.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import org.apache.logging.log4j.util.Strings;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author castle
 */
public class SignUtils {

	public static String calcSign(String secretId, String secretKey, String cDate, Map<String,Object> paramMap ) throws UnsupportedEncodingException {

		//构造签名参数集合
		Map<String,Object> signParamMap=new HashMap<>();
		signParamMap.put("cSecretId",secretId);
		signParamMap.put("cDate",cDate);
		signParamMap.putAll(paramMap);
		String signString=initParamMap(signParamMap);
		System.out.println("签名字符串:"+signString);

		//HmacSHA1摘要
		byte[] bytes=signString.getBytes(StandardCharsets.UTF_8);
		HMac mac = new HMac(HmacAlgorithm.HmacSHA1, secretKey.getBytes(StandardCharsets.UTF_8));
		String macHex1 = mac.digestHex(bytes);

		//Base64加密
		String sign = Base64.encode(macHex1);
		return sign;
	}

	/**
	 * 处理输入参数
	 * @param map
	 * @return
	 */
	public static String initParamMap(Map<String,Object> map){
		//构造签名参数集合
		Map<String,Object> signParamMap=new HashMap<>();
		if(map!=null && !map.isEmpty()){
			for(String key:map.keySet()){
				// 筛选 非null且非空的参数参与签名
				if(map.get(key)!=null && Strings.isNotEmpty(map.get(key).toString())){
					signParamMap.put(key,map.get(key));
				}
			}
		}

		//排序 signParamMap的key值按照ASCII码升序排列
		TreeMap<String,Object> treeMap=new TreeMap();
		for(String key:signParamMap.keySet()){
			treeMap.put(key,signParamMap.get(key));
		}

		//拼接 将排序后的参数与其值，组合成“参数=参数值”的格式，并且把这些参数用&字符连接来，此时生成的字符串为待签名字符串
		StringBuilder sb=new StringBuilder();
		for(String key:treeMap.keySet()){
			sb.append(key+"="+treeMap.get(key)+"&");
		}
		sb=new StringBuilder(sb.toString().substring(0,sb.lastIndexOf("&")));
		return sb.toString();
	}

	public static String genHMAC(String data, String key) {
		byte[] result = null;
		try {
			//根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
			SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			//生成一个指定 Mac 算法 的 Mac 对象
			Mac mac = Mac.getInstance("HmacSHA1");
			//用给定密钥初始化 Mac 对象
			mac.init(signinKey);
			//完成 Mac 操作
			byte[] rawHmac = mac.doFinal(data.getBytes());
			result = java.util.Base64.getEncoder().encode(rawHmac);

		} catch (NoSuchAlgorithmException e) {
			System.err.println(e.getMessage());
		} catch (InvalidKeyException e) {
			System.err.println(e.getMessage());
		}
		if (null != result) {
			return new String(result);
		} else {
			return null;
		}
	}

}


