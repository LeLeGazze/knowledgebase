package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自定义校验类型枚举
 * @author castle
 */
@Getter
@AllArgsConstructor
public enum ValidateTypeEnum {
    VALIDATE_IP(1,"IP","IP")
    ,VALIDATE_URL(2,"URL","URL")
    ,VALIDATE_PORT(3,"PORT","PORT")
    ,VALIDATE_TELPHONE(4,"固话","固话")
    ,VALIDATE_PHONE(5,"手机","手机")
    ,VALIDATE_PHONETWO(6,"固话/手机","固话/手机")
    ,VALIDATE_IDNO(7,"身份证","身份证")
    ,VALIDATE_EMAIL(8,"邮箱","邮箱")
    ,VALIDATE_INTEGER(9,"整数","整数")
    ,VALIDATE_PINTEGER(10,"正整数","正整数")
    ,VALIDATE_LOWERCASE(11,"小写字母","小写字母")
    ,VALIDATE_UPPERCASE(12,"大写字母","大写字母")
    ,VALIDATE_ALPHABETS(13,"字母","字母")
    ,VALIDATE_CHINESE(14,"中文","中文")
    ,VALIDATE_PSD(15,"密码","密码")
    ,VALIDATE_ONE_POINT(16,"一位小数","一位小数")
    ,VALIDATE_TWO_POINT(17,"两位小数","两位小数")
    ,VALIDATE_ALPHABETS_NUMBER(18,"字母数字","字母数字")
    ;
    Integer code;
    String name;
    String desc;

    /**
     * 根据code获取去value
     * @param code
     * @return
     */
    public static String getValueByCode(Integer code){
        String validataTypeName ="";
        if(ValidateTypeEnum.VALIDATE_IP.getCode().equals(code)){
            return "validateIP";
        }else if(ValidateTypeEnum.VALIDATE_URL.getCode().equals(code)){
            return "validateURL";
        }else if(ValidateTypeEnum.VALIDATE_PORT.getCode().equals(code)){
            return "validatePort";
        }else if(ValidateTypeEnum.VALIDATE_TELPHONE.getCode().equals(code)){
            return "validateTelphone";
        }else if(ValidateTypeEnum.VALIDATE_PHONE.getCode().equals(code)){
            return "validatePhone";
        }else if(ValidateTypeEnum.VALIDATE_PHONETWO.getCode().equals(code)){
            return "validatePhoneTwo";
        }else if(ValidateTypeEnum.VALIDATE_IDNO.getCode().equals(code)){
            return "validateIdNo";
        }else if(ValidateTypeEnum.VALIDATE_EMAIL.getCode().equals(code)){
            return "validateEMail";
        }else if(ValidateTypeEnum.VALIDATE_INTEGER.getCode().equals(code)){
            return "validateInteger";
        }else if(ValidateTypeEnum.VALIDATE_PINTEGER.getCode().equals(code)){
            return "validatePInteger";
        }else if(ValidateTypeEnum.VALIDATE_LOWERCASE.getCode().equals(code)){
            return "validateLowerCase";
        }else if(ValidateTypeEnum.VALIDATE_UPPERCASE.getCode().equals(code)){
            return "validateUpperCase";
        }else if(ValidateTypeEnum.VALIDATE_ALPHABETS.getCode().equals(code)){
            return "validateAlphabets";
        }else if(ValidateTypeEnum.VALIDATE_CHINESE.getCode().equals(code)){
            return "validateChinese";
        }else if(ValidateTypeEnum.VALIDATE_PSD.getCode().equals(code)){
            return "validatePsdReg";
        }else if(ValidateTypeEnum.VALIDATE_TWO_POINT.getCode().equals(code)){
            return "validateTwoPoint";
        }else if(ValidateTypeEnum.VALIDATE_ONE_POINT.getCode().equals(code)){
            return "validateOnePoint";
        }else if(ValidateTypeEnum.VALIDATE_ALPHABETS_NUMBER.getCode().equals(code)){
            return "validateAlpNum";
        }
        return  null;
    }
}
