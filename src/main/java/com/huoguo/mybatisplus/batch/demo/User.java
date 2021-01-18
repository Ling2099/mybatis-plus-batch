package com.huoguo.mybatisplus.batch.demo;

import com.huoguo.mybatisplus.batch.annotation.BatchColumns;
import com.huoguo.mybatisplus.batch.annotation.BatchId;
import com.huoguo.mybatisplus.batch.annotation.BatchLogic;
import com.huoguo.mybatisplus.batch.annotation.BatchName;
import com.huoguo.mybatisplus.batch.enums.BatchIdEnum;

/**
 * 222
 * @author 456
 */
@BatchName("user")
public class User {

    @BatchId(value = "id", type = BatchIdEnum.INPUT)
    private Long id;

    @BatchColumns("relation_id")
    private Long relationId;

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

    public Long getRelationId() {
        return relationId;
    }

    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }
}
