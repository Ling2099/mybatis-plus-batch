package com.huoguo.mybatisplus.batch.enums;

/**
 * @ClassName DateType
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/28 16:21
 * @Version 1.0
 */
public enum DateType {

    /** 用户输入 **/
    INPUT(0, ""),

    /** 当前时间（年月日 时分秒） **/
    NOW(1, "NOW()"),

    /** 当前年月日 **/
    CURDATE(2, "CURDATE()"),

    /** 当前时分秒 **/
    CURTIME(3, "CURTIME()");

    private final int key;
    private final String value;

    DateType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
