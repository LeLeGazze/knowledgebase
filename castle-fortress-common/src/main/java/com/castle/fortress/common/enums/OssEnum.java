package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件传输平台枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum OssEnum {

    SYS_LOCAL(1,"本地存储","本地存储"),
    ALIYUN(2,"阿里云","阿里云"),
    JDCLOUD(3,"京东云","京东云"),
    TENCENT_COS(4,"腾讯云","腾讯云"),
    ;

    Integer code;
    String name;
    String desc;
}
