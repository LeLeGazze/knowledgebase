package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    SYS_USER(0,"sysUser","系统用户"),
    MEMBER(1,"member","会员")
    ;

    Integer code;
    String name;
    String desc;
}
