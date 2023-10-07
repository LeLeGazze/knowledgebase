package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 请假 上午下午 枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum LeaveTypeEnum {
    AM("am","上午","上午"),
    PM("pm","下午","下午"),
    ;

    String code;
    String name;
    String desc;

    public static String getNameByCode(String code){
        for(LeaveTypeEnum e:LeaveTypeEnum.values()){
            if(e.getCode().equals(code)){
                return e.getName();
            }
        }
        return null;
    }
}
