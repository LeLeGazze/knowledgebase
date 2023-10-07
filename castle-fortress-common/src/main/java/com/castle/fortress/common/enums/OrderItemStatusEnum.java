package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单内商品 状态枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum OrderItemStatusEnum {

    AFTER_SALES_APPLICATION(1,"售后申请中","售后申请中"),
    AFTER_SALES(2,"售后中","售后中"),
    AFTER_SALES_FINISH(3,"已售后","已售后"),
    ;



    Integer code;
    String name;
    String desc;
}
