package com.castle.fortress.common.exception;

import com.castle.fortress.common.entity.IRespCode;
import com.castle.fortress.common.respcode.GlobalRespCode;
import lombok.Getter;

/**
 * 业务异常
 * @author Dawn
 */
public class BizException extends  RuntimeException{
    private static final long serialVersionUID = 2392018535894118065L;
    @Getter
    private IRespCode iRespCode;

    public BizException(String msg){
        super(msg);
        this.iRespCode= GlobalRespCode.BUSINESS_ERROR;
        this.iRespCode.setMsg(msg);
    }
    public BizException(IRespCode iRespCode,String msg){
        super(msg);
        this.iRespCode= iRespCode;
        this.iRespCode.setMsg(msg);
    }

    public BizException(IRespCode iRespCode){
        super(iRespCode.getMsg());
        this.iRespCode= iRespCode;
    }

    public BizException(IRespCode iRespCode, Throwable throwable){
        super(throwable);
        this.iRespCode= iRespCode;
    }

}
