package com.castle.fortress.admin.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 扩展字段数据类型
 */
@Getter
@AllArgsConstructor
public enum FieldTypeEnum {
    VARCHAR(1,"varchar(255)", "单行文本"),
    BIG_INT(2,"bigint(64)", "长整型"),
    INT(3,"int(11)", "整型"),
    TINYINT(4,"tinyint(1)", "TINYINT"),
    TEXT(5,"text", "TEXT"),
    LONGTEXT(6,"longtext", "LONGTEXT"),
    DATETIME(7,"datetime", "日期时间"),
    DATE(8,"date", "日期"),
    ;
    Integer code;
    String name;
    String desc;
}
