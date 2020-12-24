package com.huoguo.mybatisplus.batch.entity;

import com.huoguo.mybatisplus.batch.annotation.TableId;
import com.huoguo.mybatisplus.batch.annotation.TableName;
import lombok.Data;
import com.huoguo.mybatisplus.batch.enums.IdType;
import lombok.experimental.Accessors;

/**
 * @ClassName: User
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/24 21:49
 * @Version: 1.0
 */
@Data
@Accessors(chain = true)
@TableName(value = "user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private int sex;
}
