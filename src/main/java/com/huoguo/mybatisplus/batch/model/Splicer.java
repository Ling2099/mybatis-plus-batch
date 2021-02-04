package com.huoguo.mybatisplus.batch.model;


import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 条件构造器
 *
 * @author Lizhenghuang
 */
public class Splicer implements Serializable {

    private static final long serialVersionUID = 1868317904364006795L;
    private Map<String, Object> map;

    public Splicer () {
        this.map = new ConcurrentHashMap<>();
    }

    public Splicer where() {
        return new Splicer();
    }

    public Splicer and(String column, Object val) {
        this.map.put(column, val);
        return this;
    }

    public Splicer and(String column) {
        this.map.put(column, null);
        return this;
    }

    public Map<String, Object> getMap() {
        return map;
    }
}
