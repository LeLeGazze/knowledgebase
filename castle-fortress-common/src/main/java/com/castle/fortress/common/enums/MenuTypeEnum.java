package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * MenuType枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {
    MENU(1,"菜单","菜单"),
    INNER_PAGE(2,"内页","内页"),
    BUTTON(3,"按钮","按钮"),
    ;

    Integer code;
    String name;
    String desc;
}
