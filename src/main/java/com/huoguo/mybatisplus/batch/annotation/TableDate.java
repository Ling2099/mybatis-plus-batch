package com.huoguo.mybatisplus.batch.annotation;

import com.huoguo.mybatisplus.batch.enums.DateType;

import java.lang.annotation.*;

/**
 * 数据库日期类型字段注解
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableDate {

    /** 列名字符串 **/
    String value() default "";

    /** 数据类型 **/
    DateType type() default DateType.INPUT;
}
