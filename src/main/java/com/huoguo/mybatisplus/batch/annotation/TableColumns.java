package com.huoguo.mybatisplus.batch.annotation;

import java.lang.annotation.*;

/**
 * @ClassName TableColumns
 * @Description 表格列
 * @Author LZH
 * @Date 2020/12/28 11:04
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableColumns {

    String value() default "";
}
