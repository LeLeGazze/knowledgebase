package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 短信平台枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum SmsPlatFormEnum {
    ALIYUN(1,"阿里云","阿里云"),
    TENCENTCLOUD(2,"腾讯云","腾讯云"),
    ;

    Integer code;
    String name;
    String desc;

    public static String getNameByCode(Integer code){
        for(SmsPlatFormEnum e:SmsPlatFormEnum.values()){
            if(e.getCode().equals(code)){
                return e.getName();
            }
        }
        return null;
    }
}
