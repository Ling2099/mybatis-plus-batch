package com.huoguo.mybatisplus.batch.test.entity;

import com.huoguo.mybatisplus.batch.annotation.*;
import com.huoguo.mybatisplus.batch.enums.DateType;
import com.huoguo.mybatisplus.batch.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/28 14:38
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@TableName(value = "user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableColumns(value = "name")
    private String name;

    @TableColumns(value = "age")
    private int age;

    @TableColumns(value = "msg")
    private String msg;

    @TableColumns(value = "sex")
    private int sex;

    @TableDate(value = "insert_data", type = DateType.NOW)
    private Date insertData;

    @TableLogic(value = "is_del")
    private int isDel;
}
