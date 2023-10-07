package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限-岗位 枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum DataPermissionPostEnum {
    NO_LIMIT(1,"不限","不限"),
    SELF_SUB(2,"本人及下级岗位","本人及下级岗位"),
    SELF(3,"本人","本人"),
    ;

    Integer code;
    String name;
    String desc;
}
