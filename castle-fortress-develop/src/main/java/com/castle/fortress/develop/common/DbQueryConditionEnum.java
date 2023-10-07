package com.castle.fortress.develop.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库的运算符号枚举
 * @author castle
 */
@Getter
@AllArgsConstructor
public enum DbQueryConditionEnum {
    EQ(1,"=")
    ,UN_EQ(2,"!=")
    ,LESS_THAN(3,"<")
    ,LESS_EQ(4,"<=")
    ,GREATER_THAN(5,">")
    ,GREATER_EQ(6,">=")
    ,LIKE(7,"like")
    ;
    Integer code;
    String name;
}
