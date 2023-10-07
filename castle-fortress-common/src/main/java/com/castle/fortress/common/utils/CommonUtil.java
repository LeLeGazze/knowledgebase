package com.castle.fortress.common.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.logging.log4j.util.Strings;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用工具类
 *
 * @author Dawn
 */
public class CommonUtil {
	/**
	 * 字母数字
	 */
	public static final String RANGE1="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	/**
	 * 大写字母与数字
	 */
	public static final String RANGE2="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * 小写字母与数字
	 */
	public static final String RANGE3="0123456789abcdefghijklmnopqrstuvwxyz";
	/**
	 * 字母
	 */
	public static final String RANGE4="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	/**
	 * 数字
	 */
	public static final String RANGE5="0123456789";

	public static final Pattern humpPattern = Pattern.compile("[A-Z]");

	public static final Pattern underlinePattern = Pattern.compile("_");



	/**
	 * 获取指定长度的随机字符长
	 * @param length 字符串的长度
	 * @param range  字符串的取值范围
	 * @return
	 */
	public static String getRandomString(Integer length,String range) {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(range.length());
			sb.append(range.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 校验参数列表中是否包含null
	 * @param objects 参数列表
	 * @return true 包含null; false 不包含null
	 */
	public static boolean verifyParamNull(Object... objects){
		for(Object obj:objects){
			if(obj == null){
				return true;
			}
		}
		return false;
	}

	/**
	 * 校验参数列表中是否包含null或者是空字符串
	 * @param objects 参数列表
	 * @return true 包含null或者是空字符串; false 不包含null或者是空字符串
	 */
	public static boolean verifyParamEmpty(Object... objects){
		for(Object obj:objects){
			if(obj == null || StrUtil.isEmpty(obj.toString())){
				return true;
			}
		}
		return false;
	}

	/**
	 * 将空对象转换为空字符串
	 * @param obj
	 * @return
	 */
	public static String emptyIfNull(Object obj){
		if(obj==null|| Strings.isEmpty(obj.toString())){
			return "";
		}else {
			return obj.toString();
		}
	}

	/** 驼峰转下划线连接 */
	public static String humpToUnderline(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}


}
