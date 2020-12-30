package com.huoguo.mybatisplus.batch.annotation;

import com.huoguo.mybatisplus.batch.enums.IdType;

import java.lang.annotation.*;

/**
 * 数据库主键字段注解
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableId {

    /** 列名字符串 **/
    String value() default "";

    /** 字段烈性 **/
    IdType type() default IdType.INPUT;
}
