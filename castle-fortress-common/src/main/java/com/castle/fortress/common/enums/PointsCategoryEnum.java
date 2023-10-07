package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 积分流水类型详细类别。（为什么加或者减）
 * @author  Mgg
 */
@Getter
@AllArgsConstructor
public enum PointsCategoryEnum {
    ADD(1,"购物送积分","add"),
    REDUCE(2,"减少","reduce"),
    ;

    Integer code;
    String name;
    String desc;
}
