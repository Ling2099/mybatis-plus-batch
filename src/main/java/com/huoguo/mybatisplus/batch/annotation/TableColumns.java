package com.huoguo.mybatisplus.batch.annotation;

import java.lang.annotation.*;

/**
 * 数据库表的列名注解
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableColumns {

    /** 列名字符串 **/
    String value() default "";
}
