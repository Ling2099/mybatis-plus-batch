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
        BatchUtils.appends(sb, null, BatchConstants.DEFAULT_AND, false);

        String half = "";

        // 首先判断list传的是个什么类型
        Class clazz = list.get(BatchConstants.DEFAULT_INDEX_VALUE).getClass();
        if (this.isClazz(clazz)) {
            // 传的是包装类型


        } else {
            // 传的是对象类型
        }


        // 如果条件构造器是空，代表是以主键ID作为参数的逻辑删除
        if (splicer == null) {
            // 1.list传的是数字或字符串类型
            for (Field field : fields) {
                if (field.isAnnotationPresent(BatchLogic.class)) {
                    BatchLogic batchLogic = field.getAnnotation(BatchLogic.class);
                    half = batchLogic.value().concat(BatchConstants.DEFAULT_EQUAL).concat(batchLogic.after());
                    continue;
                }

                if (field.isAnnotationPresent(BatchId.class)) {
                    String name = field.getAnnotation(BatchId.class).value();
                    sb.append(name).append(BatchConstants.DEFAULT_IN).append(BatchConstants.LEFT_PARENTHESIS);
                }
            }

            int size = list.size();
            for (int i = 0; i < size; i++) {
                BatchUtils.appends(sb, list.get(i), BatchConstants.DEFAULT_COMMA, i == 0);
            }
            sb.append(BatchConstants.RIGHT_PARENTHESIS);

            // 2.list传的是对象类型

        }

        // 如果条件构造器不是空，先判断map的值是不是null
        // （1）如果是null，代表不根据主键ID逻辑删除
        // （2）如果不是null，SQL条件中除了主键ID，还有该条件的存在

        // 1.list传的是数字或字符串类型
        // 2.list传的是对象类型
        if (splicer != null) {

        }

        String sql = String.format(BatchSqlEnum.DELETE_LIST.getSql(), tableName, half, sb.toString());
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
}
