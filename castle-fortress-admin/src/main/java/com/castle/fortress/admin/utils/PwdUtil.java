package com.castle.fortress.admin.utils;

import cn.hutool.crypto.SecureUtil;

/**
 * 密码工具类
 * @author castle
 */
public class PwdUtil {

    public static String encode(String pwd){
        return SecureUtil.sha256(pwd);
    }

    public static boolean matches(String pwd,String dbPwd){
        return dbPwd.equals(encode(pwd));
    }

}
