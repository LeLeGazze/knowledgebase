package com.castle.fortress.admin.shiro.diversity;

import cn.hutool.json.JSONUtil;
import com.castle.fortress.admin.core.constants.GlobalConstants;
import com.castle.fortress.admin.shiro.entity.CastleUserDetail;
import com.castle.fortress.admin.utils.TokenUtil;
import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.GlobalRespCode;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * jwt token 过滤器
 * @author castle
 */
public class JwtFilter extends AuthenticatingFilter {


    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws ExpiredJwtException,Exception {
        String token = getToken(request);
        if(Strings.isEmpty(token)){
            return null;
        }
        CastleUserDetail userDetail = TokenUtil.parseToken(token);
        if(userDetail == null){
            return null;
        }
        return new CastleUserToken(userDetail);
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if(((HttpServletRequest) request).getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        String token = getToken(servletRequest);
        //设置响应头
        HttpServletResponse res=(HttpServletResponse) servletResponse;
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "*");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type,Access-Token");
        res.setHeader("Access-Control-Expose-Headers", "*");
        if(Strings.isEmpty(token)){
            res.setContentType("application/json;charset=utf-8");
            res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(GlobalRespCode.NO_LOGIN_ERROR)));
            return false;
        }
        boolean flag =false;
        try {
            flag = executeLogin(servletRequest, servletResponse);
        }catch (ExpiredJwtException e){
            res.setContentType("application/json;charset=utf-8");
            res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(GlobalRespCode.TOKEN_EXPIRED_ERROR)));
            return false;
        }catch (Exception e) {
            e.printStackTrace();
            res.setContentType("application/json;charset=utf-8");
            res.getWriter().println(JSONUtil.toJsonStr(RespBody.fail(GlobalRespCode.TOKEN_INVALID_ERROR)));
            return false;
        }
        return flag;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws ExpiredJwtException,Exception {
        AuthenticationToken token = createToken(request, response);
        if (token == null) {
            String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken must be created in order to execute a login attempt.";
            throw new IllegalStateException(msg);
        } else {
            try {
                Subject subject = this.getSubject(request, response);
                subject.login(token);
                return this.onLoginSuccess(token, subject, request, response);
            } catch (AuthenticationException var5) {
                return this.onLoginFailure(token, var5, request, response);
            }
        }
    }

    /**
     * 获取请求的token
     */
    private String getToken(ServletRequest request){
        HttpServletRequest httpRequest=(HttpServletRequest)request;
        //从header中获取token
        String token = httpRequest.getHeader(GlobalConstants.TOKEN_HEADER_KEY);
        return token;
    }
}
