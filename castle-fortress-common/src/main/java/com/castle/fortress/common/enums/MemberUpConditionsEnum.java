package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员实名类型枚举
 * @author  Mgg
 */
@Getter
@AllArgsConstructor
public enum MemberUpConditionsEnum {
    ORDER_TOTAL(1,"订单总额","orderTotal"),
    ORDER_COUNT(2,"订单次数","orderCount"),
    GOODS(3,"指定商品","goods"),
    ;

    Integer code;
    String name;
    String desc;
}
