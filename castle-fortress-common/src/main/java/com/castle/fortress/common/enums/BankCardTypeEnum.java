package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 银行卡类型
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum BankCardTypeEnum {
    DC(1,"借记卡","DC"),
    CC(2,"贷记卡","CC"),
    SCC(3,"准贷记卡","SCC"),
    DCC(4,"存贷合一卡","DCC"),
    PC(5,"预付卡","PC"),
    ;
    Integer code;
    String name;
    String desc;

    public static Integer getCodeByDesc(String desc){
        for(BankCardTypeEnum e:BankCardTypeEnum.values()){
            if(e.getDesc().equals(desc)){
                return e.getCode();
            }
        }
        return null;
    }
}
