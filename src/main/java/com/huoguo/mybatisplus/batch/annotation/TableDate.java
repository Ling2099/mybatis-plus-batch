package com.huoguo.mybatisplus.batch.annotation;

import com.huoguo.mybatisplus.batch.enums.DateType;

import java.lang.annotation.*;

/**
 * @ClassName TableDate
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/28 16:19
 * @Version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableDate {

    String value() default "";

    DateType type() default DateType.INPUT;
}
