package com.castle.fortress.admin.check.utils;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

public class IdUtil {

    public static String getId(){
        return "BC" + DateUtil.format(new Date(), "yyyyMMddHHmmss") + (int) ((Math.random() * 9 + 1) * 100000000);
    }
}
