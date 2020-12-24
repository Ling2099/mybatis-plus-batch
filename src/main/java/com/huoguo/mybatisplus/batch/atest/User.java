package com.huoguo.mybatisplus.batch.atest;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @ClassName User
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/24 13:58
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
public class User {

    private Long id;

    private String name;

    private int sex;
}
