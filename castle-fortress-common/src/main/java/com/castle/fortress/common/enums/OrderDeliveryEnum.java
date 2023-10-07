package com.castle.fortress.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单 配送方式
 * @author Mgg
 */
@Getter
@AllArgsConstructor
public enum OrderDeliveryEnum {
    EXPRESS(1, "快递", "快递"),
    SELF_LIFTING(1, "自提", "自提"),

    ;

    Integer code;
    String name;
    String desc;


}
