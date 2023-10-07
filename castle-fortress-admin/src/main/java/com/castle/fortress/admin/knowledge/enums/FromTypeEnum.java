package com.castle.fortress.admin.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sunhr
 * @create 2023/4/24 13:56
 */
@Getter
@AllArgsConstructor
public enum FromTypeEnum {
     TEXT(1,"单行文本","单行文本")
    ,MULTI_TEXT(2,"多行文本","多行文本")
    ,DATE(3,"日期","日期")
    ,attachment(4,"附件","附件")
    ,SELECT_FRAME(5,"下拉框","下拉框")
    ;
    Integer code;
    String name;
    String desc;
}
