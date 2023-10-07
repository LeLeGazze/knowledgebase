package com.castle.fortress.common.utils;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.GlobalRespCode;

import java.lang.reflect.Method;

/**
 * 反射工具类
 * @author Dawn
 */
public class ReflectUtil {
    /**
     * 通过反射调用指定类c的对象obj的set方法
     * @param c set方法所在类
     * @param obj set方法的实例对象
     * @param methodName 方法名称
     * @param paramsType 参数类型数组与参数值对应
     * @param paramsValue 参数值数组与参数类型对应
     */
    public static void setMethod(Class c,Object obj,String methodName,Class[] paramsType,Object[] paramsValue){
        if(c == null || obj == null || StrUtil.isEmpty(methodName)
                || paramsType == null || paramsType.length < 1
                || paramsValue == null || paramsValue.length < 1){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        try {
            Method method=c.getMethod(methodName, paramsType);
            method.invoke(obj, paramsValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射调用指定类c的对象obj的get方法
     * @param c get方法所在类
     * @param obj get方法的实例对象
     * @param methodName 方法名称
     * @return
     */
    public static Object getMethod(Class c,Object obj,String methodName){
        if(c == null || obj == null){
            throw new BizException(GlobalRespCode.PARAM_MISSED);
        }
        Object result=null;
        try {
            Method method=c.getMethod(methodName);
            result = method.invoke(obj);
        } catch (Exception e) {
            return null;
        }
        return result;

    }

    /**
     * 查找指定类是否有指定方法
     * @param c
     * @param methodName
     * @return
     */
    public static Boolean hasMethod(Class c,String methodName){
        if(c == null || methodName == null){
            return false;
        }
        Method[] methods = c.getDeclaredMethods();
        for(Method m:methods){
            if(m.getName().equals(methodName)){
                return true;
            }
        }
        return false;
    }
}
