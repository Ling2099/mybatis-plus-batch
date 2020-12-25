package com.huoguo.mybatisplus.batch.annotation;

import com.huoguo.mybatisplus.batch.enums.IdType;

import java.lang.annotation.*;

/**
 * @ClassName: TableId
 * @Description: 主键ID
 * @Author: LZH
 * @Date: 2020/12/24 21:48
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableId {

    String value() default "";

    IdType type() default IdType.INPUT;
}
