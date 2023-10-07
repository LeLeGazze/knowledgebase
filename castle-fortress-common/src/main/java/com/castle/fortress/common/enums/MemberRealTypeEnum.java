package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员实名类型枚举
 * @author  Mgg
 */
@Getter
@AllArgsConstructor
public enum MemberRealTypeEnum {
    PERSONAL(1,"个人","normal"),
    ENTERPRISE(2,"企业","enterprise"),
    ;

    Integer code;
    String name;
    String desc;
}
