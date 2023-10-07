package com.castle.fortress.common.exception;

import com.castle.fortress.common.entity.IRespCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import lombok.Getter;

/**
 * 错误异常
 * @author Dawn
 */
public class ErrorException extends  RuntimeException{
    private static final long serialVersionUID = 4680415750342176432L;
    @Getter
    private IRespCode iRespCode;

    public ErrorException(String msg){
        super(msg);
        this.iRespCode= GlobalRespCode.FAIL;
    }

    public ErrorException(IRespCode iRespCode){
        super(iRespCode.getMsg());
        this.iRespCode= iRespCode;
    }

    public ErrorException(IRespCode iRespCode,Throwable throwable){
        super(throwable);
        this.iRespCode= iRespCode;
    }

}