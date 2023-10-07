package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作类型
 *
 * @author Mgg
 */
@Getter
@AllArgsConstructor
public enum OperationTypeEnum {
    OTHER(1,"其它","其它"),
    QUERY(2,"查询","查询"),
    INSERT(3,"新增","新增"),
    UPDATE(4,"修改","修改"),
    DELETE(5,"删除","删除"),
    EXPORT(6,"导出","导出"),
    IMPORT(7,"导入","导入"),
    UPLOAD(8,"上传","上传"),



    ;

    Integer code;
    String name;
    String desc;
}
