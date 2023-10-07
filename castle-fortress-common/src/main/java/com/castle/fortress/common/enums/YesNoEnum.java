package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * YesNo枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum YesNoEnum {
    YES(1,"yes","是"),
    NO(2,"no","否"),
    ;

    Integer code;
    String name;
    String desc;
}
