package com.castle.fortress.common.exception;

import com.castle.fortress.common.entity.RespBody;
import com.castle.fortress.common.respcode.GlobalRespCode;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一的错误异常处理
 * @author castle
 */
@Configuration
@RestControllerAdvice
public class CastleExceptionHandler {
    /**
     *  业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RespBody handleBizException(MissingServletRequestParameterException e) {
        return RespBody.fail(GlobalRespCode.PARAM_MISSED);
    }


    /**
     *  业务异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RespBody handleBizException(BizException e) {
        return RespBody.fail(e.getIRespCode());
    }

    /**
     * 错误异常
     * @param e
     * @return
     */
    @ExceptionHandler(ErrorException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RespBody handleErrorException(ErrorException e) {
        return RespBody.fail(e.getIRespCode());
    }

    /**
     * 未捕获异常
     * @param e
     * @return
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RespBody handleError(Throwable e) {
        return RespBody.fail(GlobalRespCode.FAIL.getCode(),e.getMessage());
    }
}
