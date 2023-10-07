package com.castle.fortress.admin.message.wecom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 微信应用消息接收类型
 * 预留的
 * @author  whc
 */
@Getter
@AllArgsConstructor
public enum WeComReceiveType {

    //当touser为@all时忽略本参数
    TO_USER(1,"用户","touser"),//多个接收者用‘|’分隔，最多支持1000个  可以为@all
    TO_PARTY(2,"部门","toparty"),//，最多支持100个
    TO_TAG(3,"标签","totag"),//最多支持100个
    ;
    Integer code;
    String name;
    String desc;
}
