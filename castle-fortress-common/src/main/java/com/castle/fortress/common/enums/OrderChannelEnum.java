package com.castle.fortress.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单 下单渠道
 * @author Mgg
 */
@Getter
@AllArgsConstructor
public enum OrderChannelEnum {
    //暂不确定具体渠道  设置为默认
    DEFAULT(1, "默认", "默认"),

    ;

    Integer code;
    String name;
    String desc;


}
