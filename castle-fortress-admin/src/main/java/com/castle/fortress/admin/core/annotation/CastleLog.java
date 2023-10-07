package com.castle.fortress.admin.core.annotation;

import com.castle.fortress.common.enums.OperationTypeEnum;

import java.lang.annotation.*;

/**
 * 自定义操作日志记录注解
 *
 * @author Mgg
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CastleLog {
    /**
     * 模块
     */
    String operLocation() default "";

    /**
     * 功能
     */
    OperationTypeEnum operType() default OperationTypeEnum.OTHER;
}
