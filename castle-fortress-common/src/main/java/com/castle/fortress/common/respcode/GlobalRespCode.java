package com.castle.fortress.common.respcode;

import com.castle.fortress.common.entity.IRespCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局通用返回码 0~1999
 * @author  Dawn
 */
@Getter
@AllArgsConstructor
public enum GlobalRespCode implements IRespCode {
    /**
     * 操作成功
     */
    SUCCESS(0, "操作成功"),
    /**
     * 服务异常
     */
    FAIL(1,"服务异常"),
    /**
     * 业务异常
     */
    BUSINESS_ERROR(2,"业务异常"),
    /**
     * 请求未授权
     */
    UNAUTHORIZED(3,"请求未授权"),
    /**
     * 缺少请求参数
     */
    PARAM_MISSED(4,"缺少请求参数"),
    /**
     * 增、删、改、查 错误
     */
    OPERATE_ERROR(5,"操作失败"),
    /**
     * 数据库数据错误
     */
    DB_DATA_ERROR(6,"业务异常，请联系管理员"),
    /**
     * 用户未登录
     */
    NO_LOGIN_ERROR(401,"用户未登录"),

    /**
     * token过期
     */
    TOKEN_EXPIRED_ERROR(1000,"登录过期，请重新登录"),
    /**
     * token非法
     */
    TOKEN_INVALID_ERROR(1001,"token非法"),
    /**
     * 删除时有子节点不允许删除
     */
    DEL_HAS_CHILDREN_ERROR(1002,"请删除子节点后再删除"),
    /**
     * 验证过期,请重试
     */
    CAPTCHA_EXPIRED_ERROR(1003,"验证过期,请重试"),
    /**
     * 验证失败
     */
    CAPTCHA_VERIFY_ERROR(1004,"验证失败"),
    /**
     * 验证码错误
     */
    CAPTCHA_CODE_VERIFY_ERROR(1005,"验证码错误"),
    /**
     * 验证码过期,请重新获取
     */
    CAPTCHA_CODE_EXPIRED_ERROR(1006,"验证码过期,请重新获取"),

    /**
     * 编码错误
     */
    CHARSET_ERROR(1007,"编码错误"),

    /**
     * 名称存在
     *
     */
    NAME_ERROR(1008,"名字已经在系统中存在，请联系管理员"),

    NO_PERMISSION_ERROR(1009,"权限不足,你只能查看不能修改")
    ;
    final int code;
    String msg;

    @Override
    public void setMsg(String msg) {
        this.msg=msg;
    }
}
