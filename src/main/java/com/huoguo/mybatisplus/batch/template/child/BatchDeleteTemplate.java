package com.huoguo.mybatisplus.batch.template.child;

import com.huoguo.mybatisplus.batch.annotation.BatchId;
import com.huoguo.mybatisplus.batch.annotation.BatchLogic;
import com.huoguo.mybatisplus.batch.constant.BatchConstants;
import com.huoguo.mybatisplus.batch.enums.BatchSqlEnum;
import com.huoguo.mybatisplus.batch.model.Splicer;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.util.BatchUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @param splicer   条件构造器
     * @return 可执行的SQL字符串语句
     */
    @Override
    protected String getSql(List<?> list, Field[] fields, String tableName, Splicer splicer) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        String delPart = this.getDelPart(fields);
        Class clazz = list.get(BatchConstants.DEFAULT_INDEX_VALUE).getClass();

        if (splicer == null) {
            this.getIdPart(sb, fields);
            this.getIdVal(clazz, sb, list, size, fields);
        } else {
            Map<String, Object> map = splicer.getMap();
            map.size();

            Set set = map.keySet();
            for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                String key = (String) iter.next();
                Object obj = map.get(key);
                if ("".equals(obj.toString())) {
                    this.conditions(sb, key, clazz, list, size, fields);
                } else {
                    this.conditions(sb, key, fields, map);
                    if (!iter.hasNext()) {
                        this.getIdPart(sb, fields);
                        this.getIdVal(clazz, sb, list, size, fields);
                    }
                }
            }
        }
        return String.format(BatchSqlEnum.DELETE_LIST.getSql(), tableName, delPart, sb.toString());
    }

    /**
     * 拼接逻辑字段SQL
     *
     * @param fields 字段属性数组
     * @return 逻辑字段SQL片段
     */
    private String getDelPart(Field[] fields) {
        String part = "";
        for (Field field : fields) {
            if (field.isAnnotationPresent(BatchLogic.class)) {
                BatchLogic batchLogic = field.getAnnotation(BatchLogic.class);
                part = batchLogic.value().concat(BatchConstants.DEFAULT_EQUAL).concat(batchLogic.after());
                break;
            }
        }
        return part;
    }

    /**
     * 拼接主键ID字段SQL
     *
     * @param sb     StringBuilder
     * @param fields 字段属性数组
     */
    private void getIdPart(StringBuilder sb, Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(BatchId.class)) {
                BatchId batchId = field.getAnnotation(BatchId.class);
                BatchUtils.appends(sb, BatchConstants.DEFAULT_AND, batchId.value(), BatchConstants.DEFAULT_IN);
                break;
            }
        }
    }

    /**
     * 拼接批量操作中值的SQL
     *
     * @param clazz  类实例
     * @param sb     StringBuilder
     * @param list   数据集合
     * @param fields 字段属性数组
     */
    private void getIdVal(Class clazz, StringBuilder sb, List<?> list, int size, Field[] fields) {
        sb.append(BatchConstants.LEFT_PARENTHESIS);
        if (this.isClazz(clazz)) {
            for (int i = 0; i < size; i++) {
                BatchUtils.appends(sb, list.get(i), BatchConstants.DEFAULT_COMMA, i == 0);
            }
        } else {
            try {
                for (int i = 0; i < size; i++) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(BatchId.class)) {
                            BatchUtils.appends(sb, BatchUtils.getValue(field.getType(), field.get(list.get(i))), BatchConstants.DEFAULT_COMMA, i == 0);
                        }
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append(BatchConstants.RIGHT_PARENTHESIS);
    }

    /**
     * 判断该类实例是否为包装类型
     *
     * @param clazz 类实例
     * @return 是否
     */
    private Boolean isClazz(Class clazz) {
        return clazz == Integer.class || clazz == Long.class || clazz == String.class;
    }

    /**
     * 条件判断为Map的值为空字符串时
     *
     * @param sb     StringBuilder
     * @param key    Map的Key
     * @param clazz  类实例
     * @param list   数据集合
     * @param size   数据集合大小
     * @param fields 字段属性数组
     */
    private void conditions(StringBuilder sb, String key, Class clazz, List<?> list, int size, Field[] fields) {
        BatchUtils.appends(sb, BatchConstants.DEFAULT_AND, key, BatchConstants.DEFAULT_IN, BatchConstants.LEFT_PARENTHESIS);
        if (this.isClazz(clazz)) {
            for (int i = 0; i < size; i++) {
                BatchUtils.appends(sb, list.get(i), BatchConstants.DEFAULT_COMMA, i == 0);
            }
        } else {
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getName().equals(BatchUtils.toStr(key))) {
                        for (int i = 0; i < size; i++) {
                            BatchUtils.appends(sb, BatchUtils.getValue(field.getType(), field.get(list.get(i))), BatchConstants.DEFAULT_COMMA, i == 0);
                        }
                        break;
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append(BatchConstants.RIGHT_PARENTHESIS);
    }

    /**
     * 条件判断为Map的值为不空字符串时
     *
     * @param sb     StringBuilder
     * @param key    Map的Key
     * @param fields 字段属性数组
     * @param map    Map
     */
    private void conditions(StringBuilder sb, String key, Field[] fields, Map map) {
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals(BatchUtils.toStr(key))) {
                BatchUtils.appends(sb, BatchConstants.DEFAULT_AND, key, BatchConstants.DEFAULT_EQUAL, BatchUtils.getValue(field.getType(), map.get(key)));
                break;
            }
        }
    }
}
