package com.castle.fortress.admin.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.common.utils.CommonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 业务通用工具类
 * @author castle
 */
public class BizCommonUtil {
    /**
     * 生成密码盐值
     * @return
     */
    public static String generateSalt(){
        return CommonUtil.getRandomString(7,CommonUtil.RANGE1);
    }

    /**
     * 随机生成40位secretId
     *
     * @return
     */
    public static String autoSecretId() {
        return CommonUtil.getRandomString(40,CommonUtil.RANGE1);
    }

    /**
     * 随机生成32位secretKey
     *
     * @return
     */
    public static String autoSecretKey() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        return uuid;
    }
    /**
     * 获取分页参数
     * @param page
     * @return Map offset:偏移量 rows：记录数
     */
    public static Map<String,Long> getPageParam(Page page){
        Map<String,Long> map = new HashMap<>();
        Long current = page.getCurrent() < 1 ? GlobalConstants.DEFAULT_PAGE_INDEX:page.getCurrent();
        Long size = page.getSize()<1?GlobalConstants.DEFAULT_PAGE_SIZE:page.getSize();
        map.put("offset",(current-1)*size);
        map.put("rows",size);
        return map;
    }

    /**
     * 获取分页参数
     * @param current
     * @param size
     * @return Map offset:偏移量 rows：记录数
     */
    public static Map<String,Integer> getOffset(Integer current,Integer size){
        Map<String,Integer> map = new HashMap<>();
        current = current < 1 ? GlobalConstants.DEFAULT_PAGE_INDEX:current;
        size = size<1?GlobalConstants.DEFAULT_PAGE_SIZE:size;
        map.put("offset",(current-1)*size);
        map.put("rows",size);
        return map;
    }

    /**
     * 判断两个字符串是否相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean equalsStr(String str1, String str2){
        if(StrUtil.isEmpty(str1) && StrUtil.isEmpty(str2)){
            return true;
        }
        if(!StrUtil.isEmpty(str1) && str1.equals(str2)){
            return true;
        }
        return false;
    }
}
