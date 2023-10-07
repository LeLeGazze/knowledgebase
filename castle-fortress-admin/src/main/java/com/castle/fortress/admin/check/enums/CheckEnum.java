package com.castle.fortress.admin.check.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CheckEnum {
    NOTSTARTED(1, "NOTSTARTED", "未开始"),
    PROCESSING(2, "PROCESSING", "处理中"),
    CONVERTPDF(3, "CONVERTPDF", "转换PDF中"),
    SUCCEED(4, "SUCCEED", "成功"),
    ERROR(5, "ERROR", "失败"),
    ;
    Integer code;
    String name;
    String desc;
}
