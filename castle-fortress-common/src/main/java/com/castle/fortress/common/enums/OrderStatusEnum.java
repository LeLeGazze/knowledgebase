package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    CLOSE(1,"交易关闭","交易关闭"),
    WAIT_PAY(2,"待支付","待支付"),
    WAIT_DELIVERY(3,"待发货","待发货"),
    WAIT_RECEIPT(4,"待收货","待收货"),
    WAIT_EVALUATION(5,"待评价","待评价"),
    FINISH(6,"已完成","已完成"),
    REFUND_APPLY(7,"售后申请中","售后申请中"),
    REFUNDING(8,"售后中","售后中"),
    ;



    Integer code;
    String name;
    String desc;
}
