package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员状态枚举
 * @author  Mgg
 */
@Getter
@AllArgsConstructor
public enum MemberStatusEnum {
    NORMAL(1,"正常","normal"),
    STOP(2,"停用","stop"),
    BLACKLIST(3,"黑名单","blacklist"),
    ;

    Integer code;
    String name;
    String desc;
}
