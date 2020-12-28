package com.huoguo.mybatisplus.batch.enums;

/**
 * @ClassName SqlMethod
 * @Description SQL枚举类
 * @Author LZH
 * @Date 2020/12/25 10:55
 * @Version 1.0
 */
public enum SqlMethod {

    /** 新增 **/
    INSERT_LIST("insert", "插入集合数据（选择字段插入）", "INSERT INTO %s (%s) VALUES %s");

    private final String method;
    private final String desc;
    private final String sql;

    SqlMethod(String method, String desc, String sql) {
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
