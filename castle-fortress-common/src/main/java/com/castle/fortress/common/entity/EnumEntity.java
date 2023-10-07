package com.castle.fortress.common.entity;

import lombok.Data;

/**
 * 枚举实体类
 * @author castle
 */
@Data
public class EnumEntity {
    private String code;
    private String  name;
    private String desc;


    public EnumEntity(String code,String name){
        this.code=code;
        this.name=name;
    }

    public EnumEntity(String code,String name,String desc){
        this.code=code;
        this.name=name;
        this.desc=desc;
    }
}
