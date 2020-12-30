package com.huoguo.mybatisplus.batch.enums;

/**
 * 主键类型枚举
 * @author Lizhenghuang
 */
public enum IdType {

    /** 自增ID **/
    AUTO(0),
    /** 用户输入ID **/
    INPUT(1),
    /** 雪花ID **/
    ASSIGN_ID(2),
    /** UUID **/
    ASSIGN_UUID(3);

    private final int key;

    IdType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
