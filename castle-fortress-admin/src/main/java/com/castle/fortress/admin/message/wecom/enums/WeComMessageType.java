package com.castle.fortress.admin.message.wecom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 微信应用消息类型
 * @author  whc
 */
@Getter
@AllArgsConstructor
public enum WeComMessageType {

    TEXT(1,"文本消息","text"),
    GRAPHIC(2,"图文消息","news"),
    TEXT_CARD(3,"文本卡片","textcard"),
    ;
    Integer code;
    String name;
    String desc;
}
