package com.castle.fortress.common.entity;

import com.castle.fortress.common.respcode.GlobalRespCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * 返回对象
 * @author Dawn
 */
@Data
@NoArgsConstructor
@ToString
public class RespBody<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private boolean success;
    private T data;
    private String msg;

    private RespBody(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = GlobalRespCode.SUCCESS.getCode() == code;
    }

    private RespBody(IRespCode respCode, String msg) {
        this(respCode, null, msg);
    }

    private RespBody(IRespCode respCode, T data) {
        this(respCode, data, respCode.getMsg());
    }

    private RespBody(IRespCode respCode, T data, String msg) {
        this(respCode.getCode(), data, msg);
    }

    private RespBody(IRespCode respCode) {
        this(respCode, null, respCode.getMsg());
    }

    public static boolean isSuccess(@Nullable RespBody<?> result) {
        if(result == null){
            return false;
        }
        return GlobalRespCode.SUCCESS.getCode() == result.code;

    }

    public static boolean isNotSuccess(@Nullable RespBody<?> result) {
        return !isSuccess(result);
    }

    public static <T> RespBody<T> data(int code, T data, String msg) {
        return new RespBody(code, data, data == null ? "暂无承载数据" : msg);
    }

    public static <T> RespBody<T> data(T data, String msg) {
        return data(GlobalRespCode.SUCCESS.getCode(), data, msg);
    }
    public static <T> RespBody<T> data(T data) {
        return data(data, "操作成功");
    }


    public static <T> RespBody<T> success(String msg) {
        return new RespBody(GlobalRespCode.SUCCESS, msg);
    }

    public static <T> RespBody<T> success(IRespCode respCode) {
        return new RespBody(respCode);
    }

    public static <T> RespBody<T> success(IRespCode respCode, String msg) {
        return new RespBody(respCode, msg);
    }

    public static <T> RespBody<T> fail(IRespCode respCode) {
        return new RespBody(respCode);
    }

    public static <T> RespBody<T> fail(String msg) {
        return new RespBody(GlobalRespCode.FAIL, msg);
    }

    public static <T> RespBody<T> fail(int code, String msg) {
        return new RespBody(code, null, msg);
    }



    public static <T> RespBody<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }



}
