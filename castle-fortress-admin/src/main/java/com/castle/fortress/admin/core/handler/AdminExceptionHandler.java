package com.castle.fortress.admin.core.handler;

import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.GlobalRespCode;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 核心模块错误异常处理
 * @author castle
 */
@Configuration
@RestControllerAdvice
public class AdminExceptionHandler {

    /**
     * 请求未授权
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RespBody handleBizException(UnauthorizedException e) {
        return RespBody.fail(GlobalRespCode.UNAUTHORIZED);
    }
}
