package com.huoguo.mybatisplus.batch.template.child;

import com.huoguo.mybatisplus.batch.annotation.BatchId;
import com.huoguo.mybatisplus.batch.annotation.BatchLogic;
import com.huoguo.mybatisplus.batch.constant.BatchConstants;
import com.huoguo.mybatisplus.batch.enums.BatchSqlEnum;
import com.huoguo.mybatisplus.batch.model.Splicer;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.util.BatchUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
        // 这里是拼接前半句SQL
        String delPart = getDelPart(fields);
        Class clazz = list.get(BatchConstants.DEFAULT_INDEX_VALUE).getClass();
        // 如果条件构造器是空，代表是以主键ID作为参数的逻辑删除
        if (splicer == null) {
            this.getIdPart(sb, fields);
            this.getIdVal(clazz, sb, list, fields);
        } else {
            Map<String, Object> map = splicer.getMap();
            sb.append(BatchConstants.DEFAULT_AND);
            for (String key : map.keySet()) {
                Object obj = map.get(key);
                if ("".equals(obj.toString())) {
                    sb.append(key).append(BatchConstants.DEFAULT_IN);
                    // ( )
                    // this.getIdVal(clazz, sb, list, fields);

                    sb.append(BatchConstants.LEFT_PARENTHESIS);
                    if (this.isClazz(clazz)) {
                        for (int i = 0; i < list.size(); i++) {
                            BatchUtils.appends(sb, list.get(i), BatchConstants.DEFAULT_COMMA, i == 0);
                        }
                    } else {
                        try {
                            for (Field field : fields) {
                                field.setAccessible(true);
                                if (field.getName().equals(BatchUtils.toStr(key))) {
                                    for (int i = 0; i < list.size(); i++) {
                                        BatchUtils.appends(sb, BatchUtils.getValue(field.getType(), field.get(list.get(i))), BatchConstants.DEFAULT_COMMA, i == 0);
                                    }
                                }
                                // 要不要结束循环
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    sb.append(BatchConstants.RIGHT_PARENTHESIS);
                } else {

                }
            }
        }


        // 如果条件构造器不是空，先判断map的值是不是null
        // （1）如果是null，代表不根据主键ID逻辑删除
        // （2）如果不是null，SQL条件中除了主键ID，还有该条件的存在

        // 1.list传的是数字或字符串类型
        // 2.list传的是对象类型

        String sql = String.format(BatchSqlEnum.DELETE_LIST.getSql(), tableName, delPart, sb.toString());
        System.out.println(sql);
        return null;
    }

    /**
     * 判断该类实例是否为包装类型
     * @param clazz 类实例
     * @return 是否
     */
    private Boolean isClazz(Class clazz) {
        return clazz == Integer.class || clazz == Long.class || clazz == String.class;
    }

    /**
     * 拼接逻辑字段SQL
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
     * @param sb     StringBuilder
     * @param fields 字段属性数组
     */
    private void getIdPart(StringBuilder sb, Field[] fields) {
        for (Field field : fields) {
            if (field.isAnnotationPresent(BatchId.class)) {
                BatchId batchId = field.getAnnotation(BatchId.class);
                sb.append(BatchConstants.DEFAULT_AND).append(batchId.value()).append(BatchConstants.DEFAULT_IN);
            }
        }
    }

    /**
     * 拼接批量操作中值的SQL
     * @param clazz      类实例
     * @param sb         StringBuilder
     * @param list       数据集合
     * @param fields     字段属性数组
     */
    private void getIdVal(Class clazz, StringBuilder sb, List<?> list, Field[] fields) {
        int size = list.size();
        sb.append(BatchConstants.LEFT_PARENTHESIS);
        if (this.isClazz(clazz)) {
            // 传的是包装类型
            // 1.list传的是数字或字符串类型
            for (int i = 0; i < size; i++) {
                BatchUtils.appends(sb, list.get(i), BatchConstants.DEFAULT_COMMA, i == 0);
            }
        } else {
            // 传的是对象类型
            // 2.list传的是对象类型
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
}
