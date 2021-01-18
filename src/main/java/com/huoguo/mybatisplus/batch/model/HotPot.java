package com.huoguo.mybatisplus.batch.model;

/**
 * 存储运行时所执行的类名、方法名或值的实体类
 *
 * @author Lizhenghuang
 */
public class HotPot {

    /** 类名 **/
    private Class<?> clazz;

    /** 方法名 **/
    private String method;

    /** 实际的值 **/
    private Object val;

    public HotPot(Class<?> clazz, String method, Object val) {
        this.clazz = clazz;
        this.method = method;
        this.val = val;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
}
