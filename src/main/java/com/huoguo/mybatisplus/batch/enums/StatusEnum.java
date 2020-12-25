package com.huoguo.mybatisplus.batch.enums;

/**
 * @ClassName: StatusEnum
 * @Description: 类路径状态管理
 * @Author: LZH
 * @Date: 2020/12/5 18:32
 * @Version: 1.0
 */
public enum StatusEnum {

    /** 主键自增 **/
    AUTO(0, "com.huoguo.mybatisplus.batch.strategy.impl.AutoServiceImpl"),

    /** 用户输入主键 **/
    INPUT(1, "com.huoguo.mybatisplus.batch.strategy.impl.InputServiceImpl"),

    /** 雪花ID **/
    ASSIGN_ID(2, "com.huoguo.mybatisplus.batch.strategy.impl.AssignIdServiceImpl"),

    /** UUID **/
    ASSIGN_UUID(3, "com.huoguo.mybatisplus.batch.strategy.impl.AssignUuidServiceImpl");

    /** 状态值 **/
    private int mark;

    /** 类路径 **/
    private String path;


    public static String getPath(int mark) {
        for (StatusEnum statusEnum : StatusEnum.values()) {
            if (statusEnum.isMark() == mark) {
                return statusEnum.getPath();
            }
        }
        return null;
    }

    public int isMark() {
        return mark;
    }

    public String getPath() {
        return path;
    }

    StatusEnum(int mark, String path){
        this.mark = mark;
        this.path = path;
    }
}
