package com.huoguo.mybatisplus.batch.model;

/**
 * 存储运行时所执行的类名、方法名或值的实体类
 * @author Lizhenghuang
 */
public class HotPot {

    /** 类名 **/
    private String className;

    /** 方法名 **/
    private String methodName;

    /** 实际的值 **/
    private Object val;

    HotPot(String className, String methodName, Object val) {
        this.className = className;
        this.methodName = methodName;
        this.val = val;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }
}
