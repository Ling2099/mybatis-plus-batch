package com.huoguo.mybatisplus.batch.enums;

/**
 * SQL片段枚举类
 *
 * @author Lizhenghuang
 */
public enum BatchSqlEnum {

    /** 新增 **/
    INSERT_LIST("insert", "INSERT INTO %s (%s) VALUES %s"),

    /** 编辑 **/
    UPDATE_LIST("update", ""),

    /** 删除 **/
    DELETE_LIST("delete", "UPDATE %s SET %s WHERE 1 = 1%s");

    private final String method;
    private final String sql;

    BatchSqlEnum(String method, String sql) {
        this.method = method;
        this.sql = sql;
    }

    public String getMethod() {
        return this.method;
    }

    public String getSql() {
        return this.sql;
    }
}
