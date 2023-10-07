package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 框架绑定的API编码枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum BindApiCodeEnum {
    PLAT_CASTLE(1,"OPENAPI","自研"),
    PLAT_ALI(2,"阿里云","阿里云"),
    PLAT_TENCENT(3,"腾讯云","腾讯云"),

    SELF_CAPTCHA(10,"验证码","验证码"),
    API_KEYWORDSEXTRACTION(50,"关键词提取","关键词提取"),
    API_AUTOSUMMARIIZATION(51,"自动摘要","自动摘要"),
    API_TEXTCORRECTION(52,"文本纠错","文本纠错"),
    API_TEXTAUDIT(53,"文本审核","文本审核"),
    ;
    Integer code;
    String name;
    String desc;
}
