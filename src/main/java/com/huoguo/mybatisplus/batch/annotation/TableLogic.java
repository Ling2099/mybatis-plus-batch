package com.huoguo.mybatisplus.batch.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: TableLogic
 * @Description: 逻辑删除
 * @Author: LZH
 * @Date: 2020/12/25 0:29
 * @Version: 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableLogic {

    String value() default "";

    String delval() default "";
}
