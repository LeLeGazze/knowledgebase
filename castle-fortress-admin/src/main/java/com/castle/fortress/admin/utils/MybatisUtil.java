package com.castle.fortress.admin.utils;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.castle.fortress.common.utils.CommonUtil;

import java.util.Map;

public class MybatisUtil {

    /**
     * 将查询map转换为查询Wrapper
     * @param queryMap
     * @param <T>
     * @return
     */
    public static <T> QueryWrapper<T> queryMapToWrapper(Map<String,Object> queryMap, Class<T> clazz){
        QueryWrapper<T> wrapper=new QueryWrapper<>();
        for(String key:queryMap.keySet()){
            //带name的模糊匹配
            if(key.indexOf("name") != -1) {
                wrapper.like(StrUtil.toUnderlineCase(key), queryMap.get(key));
                //正序
            }else if(key.equals("asc")){
                String asc= CommonUtil.emptyIfNull(queryMap.get("asc"));
                String[] ascArray=asc.split(",");
                String[] ascColumn=new String[ascArray.length];
                for(int i=0;i<ascArray.length;i++){
                    ascColumn[i]= StrUtil.toUnderlineCase(ascArray[i]);
                }
                wrapper.orderByAsc(ascColumn);
                //倒序
            }else if(key.equals("desc")){
                String desc=CommonUtil.emptyIfNull(queryMap.get("desc"));
                String[] descArray=desc.split(",");
                String[] descColumn=new String[descArray.length];
                for(int i=0;i<descArray.length;i++){
                    descColumn[i]= StrUtil.toUnderlineCase(descArray[i]);
                }
                wrapper.orderByDesc(descColumn);
                //相等
            }else{
                wrapper.eq(StrUtil.toUnderlineCase(key),queryMap.get(key));
            }
        }
        return wrapper;
    }
}
