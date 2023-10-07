package com.castle.fortress.admin.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信类型
 * @author majunjie
 */
@Getter
@AllArgsConstructor
public enum LogSmsTypeEnum {

    RES(0,"注册验证码","注册验证码"),
    LOGIN(1,"登录验证码","登录验证码"),
    BANK(2,"绑定银行卡","绑定银行卡"),
    ;

    Integer code;
    String name;
    String desc;
}
