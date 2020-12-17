package com.huoguo.mybatisplus.batch.enums;

/**
 * @ClassName: SqlMethod
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/13 21:24
 * @Version: 1.0
 */
public enum SqlMethod {

    /** 插入集合数据 **/ // 这里的SQL可能会改
    INSERT_LIST("insert", "插入集合数据（选择字段插入）", "INSERT INTO %s (%s) VALUES %s");

    private final String method;
    private final String desc;
    private final String sql;

    private SqlMethod(String method, String desc, String sql) {
        this.method = method;
        this.desc = desc;
        this.sql = sql;
    }

    public String getMethod() {
        return this.method;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getSql() {
        return this.sql;
    }
}
