package com.huoguo.mybatisplus.batch.annotation;

import java.lang.annotation.*;

/**
 * 逻辑删除字段注解
 * @author Lizhenghuang
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableLogic {

    /**
     * 列名字符串
     * @return
     */
    String value() default "";

    /**
     * 默认值 删除前
     * @return
     */
    String before() default "0";

    /**
     * 默认值 删除后
     * @return
     */
    String after() default "1";
}
