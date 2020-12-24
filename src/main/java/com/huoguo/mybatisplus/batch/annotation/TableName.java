package com.huoguo.mybatisplus.batch.annotation;

import java.lang.annotation.*;

/**
 * @ClassName: TableName
 * @Description: 表名
 * @Author: LZH
 * @Date: 2020/12/24 21:33
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface TableName {

    String value() default "";
}
