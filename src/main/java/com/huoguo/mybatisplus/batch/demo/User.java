package com.huoguo.mybatisplus.batch.demo;

import com.huoguo.mybatisplus.batch.annotation.BatchColumns;
import com.huoguo.mybatisplus.batch.annotation.BatchId;
import com.huoguo.mybatisplus.batch.annotation.BatchLogic;
import com.huoguo.mybatisplus.batch.annotation.BatchName;
import com.huoguo.mybatisplus.batch.enums.BatchIdEnum;

/**
 * @ClassName: User
 * @Description: TODO
 * @Author: LZH
 * @Date: 2021/1/17 18:40
 * @Version: 1.0
 */
@BatchName("user")
public class User {

    @BatchId(value = "id", type = BatchIdEnum.INPUT)
    private Long id;

    @BatchColumns("name")
    private String name;

    @BatchLogic("is_del")
    private int isdel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }
}
