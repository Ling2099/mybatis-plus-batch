package com.huoguo.mybatisplus.batch.annotation;

import java.lang.annotation.*;

/**
 * 数据库表名注解
 * @author Lizhenghuang
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface TableName {

    /**
     * 表名字符串
     * @return
     */
    String value() default "";
}
