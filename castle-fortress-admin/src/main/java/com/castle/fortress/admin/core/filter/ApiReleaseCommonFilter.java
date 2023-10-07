package com.castle.fortress.admin.core.filter;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.core.wrapper.BodyReaderRequestWrapper;
import com.castle.fortress.admin.system.entity.ApiSecretEntity;
import com.castle.fortress.admin.system.service.ApiSecretService;
import com.castle.fortress.admin.utils.RequestUtils;
import com.castle.fortress.admin.utils.SignUtils;
import com.castle.fortress.admin.utils.SpringUtils;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.BizErrorCode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 生产环境api网关过滤器
 * @author castle
 */
public class ApiReleaseCommonFilter implements Filter {
	private CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	private ApiSecretService apiSecretService;


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("------->对外开放API过滤器：init 初始化部分对象<-------");
		ServletContext sc = filterConfig.getServletContext();
		ApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(sc);
		//初始化applicationContext
		new SpringUtils().setApplicationContext(cxt);
		if(cxt != null ){
			if(apiSecretService == null){
				apiSecretService = (ApiSecretService) cxt.getBean(ApiSecretService.class);
			}
		}
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
		//设置响应头
		HttpServletResponse res=(HttpServletResponse) servletResponse;
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "*");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
		res.setHeader("Access-Control-Expose-Headers", "*");
		//默认返回json类型
		res.setContentType("application/json;charset=utf-8");
		//获取请求参数
		String apiContentType=httpRequest.getContentType();
		if(StrUtil.isEmpty(apiContentType)){
			res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(BizErrorCode.CONTENT_TYPE_ERROR)));
			return ;
		}
		Map<String, File> fileMap=new HashMap<String,File>();
		if(apiContentType.toLowerCase().contains("multipart/form-data")){
			//获取文件列表
			MultipartHttpServletRequest multipartHttpServletRequest=multipartResolver.resolveMultipart(httpRequest);
			httpRequest=multipartHttpServletRequest;
		}
		BodyReaderRequestWrapper apiRequest = new BodyReaderRequestWrapper(httpRequest);

		//校验请求时间是否已超时 1min
		String apiDate = apiRequest.getHeader(GlobalConstants.REQUEST_HEADER_DATE);
		if(StrUtil.isEmpty(apiDate)){
			res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(BizErrorCode.DEFECT_HEAD.getCode(),BizErrorCode.DEFECT_HEAD.getMsg()+":"+GlobalConstants.REQUEST_HEADER_DATE)));
			return ;
		}
		try {
			Date apiDateD= DateUtil.parse(apiDate,"yyyy-MM-dd HH:mm:ss");
			Date expireTime=DateUtil.offsetMinute(apiDateD,3);
			Date t = DateUtil.offsetMinute(new Date(),3);
			if(expireTime.compareTo(new Date()) == -1){
				res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(BizErrorCode.TIME_EXPIRE_ERROR)));
				return;
			}
			if(apiDateD.compareTo(t) == 1){
				res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(BizErrorCode.REQUEST_DATE_ADVANCED_ERROR)));
				return;
			}
			//日期格式错误
		} catch (Exception e) {
			e.printStackTrace();
			res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(BizErrorCode.REQUEST_DATE_ERROR)));
			return;
		}

		//HeadMap 初始化
		Enumeration<String> headNames = apiRequest.getHeaderNames();
		Map<String,String> headMap=new HashMap<String,String>();
		while(headNames.hasMoreElements()){
			String headNameTemp=headNames.nextElement();
			headMap.put(headNameTemp,apiRequest.getHeader(headNameTemp));
		}
		//获取请求参数
		String bodyParams="";
		if(apiContentType.toLowerCase().contains("application/json")){
			// body参数 application/json
			bodyParams=apiRequest.getBody();
		}else{
			Map<String,Object> bodyMap=RequestUtils.getBodyParam(apiRequest);
			bodyParams=JSONUtil.toJsonStr(bodyMap);
		}

		String apiSecretId = apiRequest.getHeader(GlobalConstants.REQUEST_HEADER_SECRET_ID);
		if(Strings.isEmpty(apiSecretId)){
			res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(BizErrorCode.DEFECT_HEAD.getCode(),BizErrorCode.DEFECT_HEAD.getMsg()+":"+GlobalConstants.REQUEST_HEADER_SECRET_ID)));
			return ;
		}
		RespBody<ApiSecretEntity> secretEntityRespBody = apiSecretService.querySecretKey(apiSecretId);
		if(!secretEntityRespBody.isSuccess()){
			res.getWriter().println(JSONUtil.toJsonStr(secretEntityRespBody));
			return ;
		}
		String secretKey = secretEntityRespBody.getData().getSecretKey();
		String apiSign = apiRequest.getHeader(GlobalConstants.REQUEST_HEADER_SIGN);
		Map<String, Object> signMap = RequestUtils.getAllParams(apiRequest);
		if (Strings.isNotEmpty(bodyParams)) {
			signMap.putAll(JSONUtil.toBean(bodyParams, Map.class));
		}
		//计算加密结果
		String calcSign= SignUtils.calcSign(apiSecretId,secretKey,apiDate,signMap);

		//校验签名
		if(!calcSign.equals(apiSign)){
			res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(BizErrorCode.SIGN_ERROR)));
			return;
		}
		filterChain.doFilter(apiRequest, servletResponse);

	}

	@Override
	public void destroy() {
	}


}
