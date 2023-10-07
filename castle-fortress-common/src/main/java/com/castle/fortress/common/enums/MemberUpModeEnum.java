package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员升级方式
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum MemberUpModeEnum {
    FINISH(1,"订单完成后","finish"),
    PAYMENT(2,"订单付款后","payment"),
    ;

    Integer code;
    String name;
    String desc;
}
