package com.castle.fortress.admin.shiro.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.io.Serializable;

/**
 * 登录表单
 *
 */
@Data
@ApiModel(value = "登录表单")
public class LoginUser {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 短信验证码
     */
    private String smscode;

    /**
     * 微信unionId
     */
    private String unionId;

    /**
     * openid
     */
    private String openid;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;


}
