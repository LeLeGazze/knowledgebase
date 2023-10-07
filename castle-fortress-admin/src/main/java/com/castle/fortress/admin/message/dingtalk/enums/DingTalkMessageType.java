package com.castle.fortress.admin.message.dingtalk.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 钉钉消息类型
 * @author  whc
 */
@Getter
@AllArgsConstructor
public enum DingTalkMessageType {

    TEXT(1,"文本消息","text"),
    TEXT_CARD(2,"卡片消息","action_card"),
    ;
    Integer code;
    String name;
    String desc;
}
