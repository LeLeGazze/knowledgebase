package com.castle.fortress.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定时任务状态枚举
 * @author  castle
 */
@Getter
@AllArgsConstructor
public enum JobStatusEnum {
    UN_START(0,"未启动","未启动"),
    RUN(1,"已启动","已启动"),
    PAUSE(2,"暂停","暂停")
    ;

    Integer code;
    String name;
    String desc;
}
