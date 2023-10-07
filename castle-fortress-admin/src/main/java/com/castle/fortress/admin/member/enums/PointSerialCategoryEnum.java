package com.castle.fortress.admin.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 积分流水类型
 */
@Getter
@AllArgsConstructor
public enum PointSerialCategoryEnum {
    ADMIN_UPDATE(1,"管理员修改", "管理员修改"),
    ADMIN_INC(2,"管理员增加", "管理员增加"),
    ADMIN_DEC(3,"管理员减少", "管理员减少"),
    GOODS_BUY(4,"商品购买", "商品购买"),
    ;
    Integer code;
    String name;
    String desc;
}
