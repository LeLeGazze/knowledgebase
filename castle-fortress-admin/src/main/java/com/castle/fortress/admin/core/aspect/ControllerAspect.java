package com.castle.fortress.admin.core.aspect;

import com.castle.fortress.common.utils.ReflectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * controller切面
 * @author castle
 */
@Aspect
@Order(1)
@Component
public class ControllerAspect {
	/**
	 * 将参数中的dataAuthFlag置为false
	 * 该字段只允许后台注解开启使用不允许前端传入
	 * @param joinPoint
	 */
	@Before(value = "execution(*  com.castle.fortress.admin..controller.*.*(..))")
	public void saveBefore(JoinPoint joinPoint){
		try {
			Object[] args=joinPoint.getArgs();
			if(args!=null && args.length>0){
				for(int i=0;i< args.length;i++){
					Object param=args[i];
					if(param!=null){
						if(Map.class.isAssignableFrom(param.getClass())){
							Map paramMap = (Map)param;
							if(paramMap.get("dataAuthFlag") !=null && Boolean.parseBoolean(paramMap.get("dataAuthFlag").toString())){
								paramMap.remove("dataAuthFlag");
							}
						}else{
							Object dataAuthFlag =ReflectUtil.getMethod(param.getClass(),param,"getDataAuthFlag");
							if(dataAuthFlag !=null && Boolean.parseBoolean(dataAuthFlag.toString())){
								ReflectUtil.setMethod(param.getClass(),param,"setDataAuthFlag",new Class[]{Boolean.class},new Object[]{false});
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
