package com.castle.fortress.admin.core.annotation;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author castle
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataAuth {
    /**
     * 表的别名
     */
    String tableAlias() default "";

}