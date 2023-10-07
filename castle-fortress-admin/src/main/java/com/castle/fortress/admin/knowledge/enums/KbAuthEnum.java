package com.castle.fortress.admin.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *知识库下的知识枚举
 */
@Getter
@AllArgsConstructor
public enum KbAuthEnum  {

    SHOW(1,"仅可查看","可查看知识"),
    DOWNLOAD(2,"可下载","可查看、下载知识"),
    ADD(3,"可新增","可查看、批量添加知识"),
    UPDATE(4,"可编辑","可查看、新增、编辑、批量添加、下载知识"),
    MANAGE(5,"可管理","可查看、新增、编辑、删除、批量添加、下载知识、删除评论"),
    ;
    Integer code;
    String name;
    String desc;

}
