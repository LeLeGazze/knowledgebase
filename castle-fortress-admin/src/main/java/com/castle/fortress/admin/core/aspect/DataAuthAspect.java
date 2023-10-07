package com.castle.fortress.admin.core.aspect;

import com.castle.fortress.common.utils.ReflectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 数据权限，切面处理类
 * 将带有 @DataAuth 注解的对象或者参数的数据权限标志置为true
 * @author Castle
 */
@Order(2)
@Aspect
@Component
public class DataAuthAspect {

    @Pointcut("@annotation(com.castle.fortress.admin.core.annotation.DataAuth)")
    public void dataAuthCut() {

    }

    @Before("dataAuthCut()")
    public void dataAuth(JoinPoint point) {
        Object[] args = point.getArgs();
        if(args!=null && args.length>0){
            for(int i=0;i< args.length;i++){
                Object param=args[i];
                if(param!=null){
                    if(Map.class.isAssignableFrom(param.getClass())){
                        Map paramMap = (Map)param;
                        paramMap.put("dataAuthFlag",true);
                    }else{
                        if(ReflectUtil.hasMethod(param.getClass(),"setDataAuthFlag")){
                            ReflectUtil.setMethod(param.getClass(),param,"setDataAuthFlag",new Class[]{Boolean.class},new Object[]{true});
                        }
                    }
                }
            }
        }
    }
}