package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 集合数据类型枚举
 * @author castle
 */
@Getter
@AllArgsConstructor
public enum ListDataTypeEnum {
    DICT_TYPE(1,"字典")
    ,ENUM_TYPE(2,"枚举")
    ,URL_TYPE(3,"接口地址")
    ,JSON_TYPE(4,"JSON常量")

    ;
    Integer code;
    String name;
}
