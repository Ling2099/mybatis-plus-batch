package com.huoguo.mybatisplus.batch.enums;

/**
 * @ClassName: IdType
 * @Description: 主键类型枚举
 * @Author: LZH
 * @Date: 2020/12/24 21:51
 * @Version: 1.0
 */
public enum IdType {

    /** 自增ID **/
    AUTO(0),
    /** 用户自定义ID **/
    NONE(1),
    /** 用户输入ID **/
    INPUT(2),
    /** 雪花ID **/
    ASSIGN_ID(3),
    /** UUID **/
    ASSIGN_UUID(4);

    private final int key;

    IdType(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
