package com.castle.fortress.admin.core.aspect;

import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.core.annotation.CastleLog;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.log.entity.LogLoginEntity;
import com.castle.fortress.admin.log.entity.LogOpenapiEntity;
import com.castle.fortress.admin.log.entity.LogOperationEntity;
import com.castle.fortress.admin.log.service.LogLoginService;
import com.castle.fortress.admin.log.service.LogOpenapiService;
import com.castle.fortress.admin.log.service.LogOperationService;
import com.castle.fortress.admin.system.entity.SysUser;
import com.castle.fortress.admin.utils.HttpUtil;
import com.castle.fortress.admin.utils.TokenUtil;
import com.castle.fortress.admin.utils.WebUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.exception.ErrorException;
import com.castle.fortress.common.respcode.GlobalRespCode;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * controller层日志切面
 * @author castle
 */
@Aspect
@Order(3)
@Component
public class LogAspect {
	@Value("${castle.logs.opt}")
	private boolean optFlag;
	@Value("${castle.logs.login}")
	private boolean loginFlag;
	@Value("${castle.logs.errorOpt}")
	private boolean errorOptFlag;
	@Value("${castle.logs.openApi}")
	private boolean openApiFlag;

	@Autowired
	private LogOperationService logOperationService;
	@Autowired
	private LogLoginService logLoginService;
	@Autowired
	private LogOpenapiService logOpenapiService;

	/**
	 * 错误日志、登录日志、操作日志
	 * @param point
	 * @return
	 */
	@Around(value = "execution(*  com.castle.fortress.admin..controller.*.*(..))")
	public Object  controllerAround(ProceedingJoinPoint point) throws Exception {
		long startTime = System.currentTimeMillis();
		Date invokeTime = new Date();
        Object result = null;
        boolean errorFlag= false;
        try {
			result = point.proceed();
		}catch(BizException exception){
        	result = RespBody.fail(exception.getIRespCode());
		}catch(ErrorException exception){
			errorFlag=true;
			result = RespBody.fail(exception.getIRespCode());
        } catch (Throwable throwable) {
			errorFlag=true;
            throwable.printStackTrace();
            result = RespBody.fail(GlobalRespCode.OPERATE_ERROR);
        }
		if(result == null){
			result = "";
		}
		//耗时 毫秒
		long elapsedTime= System.currentTimeMillis()-startTime;
		HttpServletRequest request = WebUtil.request();
    //请求路径
    String uri = request.getServletPath();
    String params = point.getArgs().length == 0?"":Arrays.toString(point.getArgs());
    String ipAddress = WebUtil.getIpAddress(request);
		String className = point.getSignature().getDeclaringTypeName();
		String methodName = point.getSignature().getName();
		//白名单不获取用户信息
		SysUser user=null;
		if(!TokenUtil.isWhiteList(uri)){
			user = WebUtil.currentUser();
		}
		Long userId = user == null?null:user.getId();
		String userName = user == null?null:user.getLoginName();
		String resultStr = result.toString();
		if(resultStr.length()>65535){
			resultStr= resultStr.substring(0,55535);
		}
		//异常
		if(errorFlag){
			//错误日志
			if(errorOptFlag){
				LogOperationEntity logOperationEntity = new LogOperationEntity(uri,params,className,methodName,"01",userId,userName,invokeTime,resultStr,elapsedTime);
				logOperationService.saveLog(logOperationEntity);
			}
		}else{
			//登录日志
//			if(uri.indexOf("/login") == 0 && loginFlag){
//				Object[] args = point.getArgs();
//				if(args.length>0){
//					params = args[0].toString();
//				}
//				LogLoginEntity logLoginEntity = null;
//				RespBody respResult = (RespBody)result;
//				if(respResult.isSuccess()){
//					logLoginEntity = new LogLoginEntity(uri,ipAddress,params,"00",invokeTime,respResult.getData().toString(),elapsedTime);
//				}else{
//					logLoginEntity = new LogLoginEntity(uri,ipAddress,params,"01",invokeTime,respResult.getMsg(),elapsedTime);
//				}
//				String ipInfo="";
//				//将ip转换为地址
//				if(WebUtil.internalIp(ipAddress)){
//					ipInfo="内网地址";
//				}else{
//					Map<String,String> query= new HashMap<>();
//					query.put("ip",ipAddress);
//					query.put("json","true");
//					String rspStr = HttpUtil.doRequest("get","http://whois.pconline.com.cn/ipJson.jsp",null, query,null);
//					if (StrUtil.isNotEmpty(rspStr))
//					{
//						com.alibaba.fastjson.JSONObject obj = com.alibaba.fastjson.JSONObject.parseObject(rspStr);
//						String pro = obj.getString("pro");
//						String city = obj.getString("city");
//						ipInfo= pro+"/"+city;
//					}
//				}
//				//地址信息
//				logLoginEntity.setAddress(ipInfo);
//				//获取浏览器信息
//				Browser browser = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getBrowser();
//				Version version =browser.getVersion(request.getHeader("User-Agent"));
//				logLoginEntity.setCusBrowser(browser.getName()+"/"+version.getVersion());
//				//操作系统
//				OperatingSystem os = UserAgent.parseUserAgentString(request.getHeader("User-Agent")).getOperatingSystem();
//				logLoginEntity.setCusOs(os==null?"未知系统":os.getName());
//				logLoginService.saveLog(logLoginEntity);
//			//普通操作日志 剔除会员操作
//			}else
			if(uri.indexOf("/api/") != 0  && optFlag){
				//保存操作日志
				LogOperationEntity logOperationEntity = new LogOperationEntity(uri,params,className,methodName,"00",userId,userName,invokeTime,resultStr,elapsedTime);
				//获取注解信息
				CastleLog controllerLog = getAnnotationLog(point);
				if (controllerLog != null){
					logOperationEntity.setOperLocation(controllerLog.operLocation());
					logOperationEntity.setOperType(controllerLog.operType().getCode());
				}
				logOperationService.saveLog(logOperationEntity);
			}
		}
        return result;
	}

	/**
	 * 是否存在注解，如果存在就获取
	 */
	private CastleLog getAnnotationLog(ProceedingJoinPoint point) throws Exception
	{
		Signature signature = point.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method method = methodSignature.getMethod();

		if (method != null)
		{
			return method.getAnnotation(CastleLog.class);
		}
		return null;
	}


	/**
	 * 开放api调用日志
	 * @param point
	 * @return
	 */
	@Around(value = "execution(*  com.castle.fortress.admin..api.*.*(..))")
	public Object  openApiAround(ProceedingJoinPoint point){
		long startTime = System.currentTimeMillis();
		Date invokeTime = new Date();
		Object result = null;
		try {
			result = point.proceed();
		}catch(BizException exception){
			result = RespBody.fail(exception.getIRespCode());
		}catch(ErrorException exception){
			result = RespBody.fail(exception.getIRespCode());
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			result = RespBody.fail(GlobalRespCode.OPERATE_ERROR);
		}
		if(openApiFlag){
			//耗时 毫秒
			long elapsedTime= System.currentTimeMillis()-startTime;
			HttpServletRequest request = WebUtil.request();
			//请求路径
			String uri = request.getServletPath();
			String params = point.getArgs().length == 0?"":Arrays.toString(point.getArgs());
			String ipAddress = WebUtil.getIpAddress(request);
			String className = point.getSignature().getDeclaringTypeName();
			String methodName = point.getSignature().getName();
			String apiSecretId = request.getHeader(GlobalConstants.REQUEST_HEADER_SECRET_ID);
			LogOpenapiEntity logOpenapiEntity = null;
			if(result instanceof RespBody){
				RespBody respResult = (RespBody)result;
				if(respResult.isSuccess()){
					logOpenapiEntity = new LogOpenapiEntity(ipAddress,uri,params,className,methodName,"00",apiSecretId,invokeTime,result.toString(),elapsedTime);
				}else{
					logOpenapiEntity = new LogOpenapiEntity(ipAddress,uri,params,className,methodName,"01",apiSecretId,invokeTime,result.toString(),elapsedTime);
				}
			}else{
				logOpenapiEntity = new LogOpenapiEntity(ipAddress,uri,params,className,methodName,"00",apiSecretId,invokeTime,null,elapsedTime);
			}
			logOpenapiService.saveLog(logOpenapiEntity);
		}
		return result;
	}


}
