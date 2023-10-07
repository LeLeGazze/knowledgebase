package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 积分流水类型
 * @author  Mgg
 */
@Getter
@AllArgsConstructor
public enum PointsTypeEnum {
    ADD(1,"增加","add"),
    REDUCE(2,"减少","reduce"),
    ;

    Integer code;
    String name;
    String desc;
}
