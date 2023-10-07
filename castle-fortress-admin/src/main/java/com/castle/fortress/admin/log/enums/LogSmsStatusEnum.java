package com.castle.fortress.admin.log.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信状态
 * @author majunjie
 */
@Getter
@AllArgsConstructor
public enum LogSmsStatusEnum {

    VALID(0,"有效","短信有效可验证"),
    INVALID(1,"无效","短信过期失效"),
    ;

    Integer code;
    String name;
    String desc;
}
