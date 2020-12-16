package com.huoguo.mybatisplus.batch.constant;

/**
 * @ClassName: DefaultConstants
 * @Description: 默认通用常量类
 * @Author: LZH
 * @Date: 2020/12/13 16:51
 * @Version: 1.0
 */
public final class DefaultConstants {

    /** 默认集合第一个下标 **/
    public static final int DEFAULT_INDEX_VALUE = 0;

    /** 默认执行批量操作的数量 **/
    public static final int DEFAULT_BATCH_SIZE = 500;

    /** 默认 -- 左括号 **/
    public static final String LEFT_PARENTHESIS = "(";

    /** 默认 -- 有括号 **/
    public static final String RIGHT_PARENTHESIS = ")";


    /** 主键策略 AUTO **/
    public static final int AUTO = 0;

    /** 主键策略 NONE **/
    public static final int NONE = 1;

    /** 主键策略 INPUT **/
    public static final int INPUT = 2;

    /** 主键策略 ASSIGN_ID **/
    public static final int ASSIGN_ID = 3;

    /** 主键策略 ASSIGN_UUID **/
    public static final int ASSIGN_UUID = 4;
}
