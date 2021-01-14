package com.huoguo.mybatisplus.batch.test;

import com.huoguo.mybatisplus.batch.annotation.*;
import com.huoguo.mybatisplus.batch.enums.BatchIdEnum;

import javax.xml.crypto.Data;
import java.util.Date;


@BatchName(value = "user")
public class User {

    @BatchId(value = "id", type = BatchIdEnum.ASSIGN_ID)
    private Long id;

    @BatchColumns(value = "name")
    private String name;

    @BatchColumns(value = "age")
    private int age;

    @BatchLogic(value = "is_del")
    private int isDel;

    @BatchIgnore
    @BatchFill(value = "create_time", insert = true)
    private Data createTime;

    @BatchIgnore
    @BatchFill(value = "update_time", update = true)
    private Date updateTime;

    @BatchIgnore
    @BatchFill(value = "creator", insert = true)
    private String creator;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public Data getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Data createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
