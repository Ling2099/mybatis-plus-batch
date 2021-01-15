package com.huoguo.mybatisplus.batch.template.child;

import com.huoguo.mybatisplus.batch.template.AbstractTemplate;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 批量删除拼接SQL逻辑
 *
 * @author Lizhenghuang
 */
public class BatchDeleteTemplate extends AbstractTemplate {

    /**
     * 获取SQL字符串语句
     *
     * @param list      数据集合
     * @param fields    类的属性数组
     * @param tableName 表名
     * @return 可执行的SQL字符串语句
     */
    @Override
    protected String getSql(List<?> list, Field[] fields, String tableName) {

        return null;
    }
}
