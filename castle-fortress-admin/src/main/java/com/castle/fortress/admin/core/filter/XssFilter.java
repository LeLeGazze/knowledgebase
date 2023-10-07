package com.castle.fortress.admin.core.filter;


import cn.hutool.core.util.StrUtil;
import com.castle.fortress.admin.core.wrapper.BodyReaderRequestWrapper;
import com.castle.fortress.admin.utils.SpringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Xss过滤器
 * @author Dawn
 */
public class XssFilter implements Filter {
	private CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("------->XssFilter：init 初始化部分对象<-------");
		ServletContext sc = filterConfig.getServletContext();
		ApplicationContext cxt = WebApplicationContextUtils.getWebApplicationContext(sc);
		//初始化applicationContext
		new SpringUtils().setApplicationContext(cxt);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)  throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
		//获取请求参数
		String apiContentType=httpRequest.getContentType();
		if(StrUtil.isNotEmpty(apiContentType) && apiContentType.toLowerCase().contains("multipart/form-data")){
			//获取文件列表
			MultipartHttpServletRequest multipartHttpServletRequest=multipartResolver.resolveMultipart(httpRequest);
			httpRequest=multipartHttpServletRequest;
		}
		BodyReaderRequestWrapper requestWrapper = new BodyReaderRequestWrapper(httpRequest);
		filterChain.doFilter(requestWrapper, servletResponse);
	}

	@Override
	public void destroy() {
	}




}
