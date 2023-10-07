package com.castle.fortress.admin.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 积分流水类型
 */
@Getter
@AllArgsConstructor
public enum PointSerialTypeEnum {
    UPDATE(1,"修改", "修改"),
    INC(2,"增加", "增加"),
    DEC(3,"减少", "减少"),
    ;
    Integer code;
    String name;
    String desc;
}
