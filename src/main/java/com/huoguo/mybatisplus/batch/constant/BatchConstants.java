package com.huoguo.mybatisplus.batch.constant;

/**
 * 默认通用常量类
 * @author Lizhenghuang
 */
public final class BatchConstants {

    /** 默认集合第一个下标 **/
    public static final int DEFAULT_INDEX_VALUE = 0;

    /** 默认执行批量操作的数量 **/
    public static final int DEFAULT_BATCH_SIZE = 1000;

    /** 默认值 左括号 **/
    public static final String LEFT_PARENTHESIS = "(";

    /** 默认值 有括号 **/
    public static final String RIGHT_PARENTHESIS = ")";

    /** 默认值 逗号 **/
    public static final String DEFAULT_COMMA = ", ";

    /** 时间字段名 **/
    @Deprecated
    public static final String DATE_COLUMN = "date_column";

    /** 时间字段值 **/
    @Deprecated
    public static final String DATE_VALUE = "date_value";

    /** 逻辑删除字段名 **/
    @Deprecated
    public static final String LOGIC_COLUMN = "logic_column";

    /** 逻辑删除字段值 **/
    @Deprecated
    public static final String LOGIC_VALUE = "logic_value";

    /** 默认ID策略 用户默认输入 **/
    @Deprecated
    public static final String DEFAULT_INPUT = "INPUT";

    /** 默认ID策略 雪花ID **/
    @Deprecated
    public static final String DEFAULT_ASSIGN_ID = "ASSIGN_ID";

    /** 默认ID策略 UUID **/
    @Deprecated
    public static final String DEFAULT_ASSIGN_UUID = "ASSIGN_UUID";

    /** 默认忽略字段 **/
    public static final String DEFAULT_IGNORE = "IGNORE";

    /** 默认字段列值 **/
    public static final String DEFAULT_VALUE = "DEFAULT";

    /** 默认SQL条件拼接字段 **/
    public static final String DEFAULT_EQUAL = " = ";

    /** 默认SQL条件拼接字段 **/
    public static final String DEFAULT_AND = " AND ";

    /** 默认SQL条件拼接字段 **/
    public static final String DEFAULT_IN = " IN ";

    /** 默认新增填充Bean Name **/
    @Deprecated
    public static final String FILL_NAME_INSERT = "insert";

    /** 默认修改填充Bean Name **/
    @Deprecated
    public static final String FILL_NAME_UPDATE = "update";

    /** 默认数据源Bean Name **/
    public static final String DATA_SOURCE = "BatchSource";
}
