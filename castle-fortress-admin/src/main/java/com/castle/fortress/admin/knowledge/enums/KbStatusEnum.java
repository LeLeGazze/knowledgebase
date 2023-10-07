package com.castle.fortress.admin.knowledge.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文章状态枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum KbStatusEnum {

    DRAFT(0,"草稿","草稿"),
    TOAUDIT(1,"待审核","待审核"),
    PUBLISHED(2,"已发布","已发布"),
    UNPASS(3,"审核未通过","审核未通过"),
    ;

    Integer code;
    String name;
    String desc;

    public static String getNameByCode(Integer code){
        for(KbStatusEnum e: KbStatusEnum.values()){
            if(e.getCode().equals(code)){
                return e.getName();
            }
        }
        return null;
    }
}
