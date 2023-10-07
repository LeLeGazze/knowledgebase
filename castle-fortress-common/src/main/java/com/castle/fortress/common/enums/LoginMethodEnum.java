package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 登录方式
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum LoginMethodEnum {
    PWD(1,"账号密码登录","账号密码登录"),
    CODE(2,"验证码登录","验证码登录"),
    WX(3,"微信登录","微信登录"),
    APPLET(4,"微信小程序登录","微信小程序登录"),
    ALIPAY(5,"支付宝登录","支付宝登录"),
    QQ(6,"QQ登录","QQ登录"),
    WX_WORK(7,"企微登录","企微登录"),
    DING(8,"钉钉登录","钉钉登录"),
    ;

    Integer code;
    String name;
    String desc;
}
