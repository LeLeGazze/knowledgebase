package com.castle.fortress.common.enums;


import com.castle.fortress.common.exception.BizException;
import com.castle.fortress.common.respcode.BizErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单 支付方式
 * @author Mgg
 */
@Getter
@AllArgsConstructor
public enum OrderPayTypeEnum {

    balance_payment(1, "余额支付", "余额支付"),

    CASH_ON_DELIVERY(2, "货到付款", "货到付款"),

    BACKGROUND_PAYMENT(3, "后台付款", "后台付款"),

    WECHAT_PAYMENT(4, "微信支付", "微信支付"),

    ALI_PAY(5, "支付宝支付", "支付宝支付"),

    UNIONPAY_PAYMENT(6, "银联支付", "银联支付"),

    ;

    Integer code;
    String name;
    String desc;


}
