package com.castle.fortress.admin.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum WhAuthEnum {

    SHOW(1, "仅可查看","可查看知识库"),
    MANAGE(2, "可管理","可新增子分类/可查看/可编辑/可删除知识库"),
    ;
    Integer code;
    String name;
    String desc;

}

