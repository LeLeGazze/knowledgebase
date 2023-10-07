package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态枚举 -内部流转
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum OrderInsideStatusEnum {
    //交易关闭
    MEMBER_CANCEL(1,"用户取消","交易关闭"),
    TIMEOUT_CANCEL(2,"超时取消","交易关闭"),
    REFUND_FINISH(3,"退款完成","交易关闭"),

    WAIT_PAY(4,"待支付","待支付"),
    WAIT_DELIVERY(5,"待发货","待发货"),
    WAIT_RECEIPT(6,"待收货","待收货"),
    WAIT_EVALUATION(7,"待评价","待评价"),
    //已完成
    FINISH(8,"用户评价完成","已完成"),
    PART_REFUND(9,"部分退款","已完成"),
    //售后申请中
    WAIT_DELIVERY_REFUND_APPLY(10,"待发货售后申请","售后申请中"),
    WAIT_RECEIPT_REFUND_APPLY(11,"待收货售后申请","售后申请中"),
    WAIT_EVALUATION_REFUND_APPLY(12,"待评价售后申请","售后申请中"),
    FINISH_REFUND_APPLY(13,"已完成售后申请","售后申请中"),

    REFUNDING(14,"售后中","售后中"),
    ;
    Integer code;
    String name;
    String desc;
}
