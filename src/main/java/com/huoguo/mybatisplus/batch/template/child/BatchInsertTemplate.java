package com.huoguo.mybatisplus.batch.template.child;

import com.huoguo.mybatisplus.batch.annotation.*;
import com.huoguo.mybatisplus.batch.constant.BatchConstants;
import com.huoguo.mybatisplus.batch.enums.BatchIdEnum;
import com.huoguo.mybatisplus.batch.enums.BatchSqlEnum;
import com.huoguo.mybatisplus.batch.model.HotPot;
import com.huoguo.mybatisplus.batch.model.Splicer;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.util.BatchBean;
import com.huoguo.mybatisplus.batch.util.BatchSnow;
import com.huoguo.mybatisplus.batch.util.BatchUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 批量新增拼接SQL逻辑
 *
 * @author Lizhenghuang
 */
public class BatchInsertTemplate extends AbstractTemplate {

    private String mark = BatchConstants.DEFAULT_COMMA;

    /**
     * 获取SQL字符串语句
     *
     * @param list      数据集合
     * @param fields    类的属性数组
     * @param tableName 表名
     * @param splicer   条件构造器（未用）
     * @return 可执行的SQL字符串语句
     */
    @Override
    protected String getSql(List list, Field[] fields, String tableName, Splicer splicer) {
        int len = fields.length;
        Map<String, Object> map = new ConcurrentHashMap<>(len);
        StringBuilder sb = new StringBuilder();

        int offset = 0;

        try {
            for (int i = 0; i < len; i++) {
                boolean isStart = i == offset;
                fields[i].setAccessible(true);

                if (BatchUtils.isStatic(fields[i])) {
                    if (i == 0 && offset == 0) {
                        offset = 1;
                    }
                    continue;
                }

                if (fields[i].isAnnotationPresent(BatchIgnore.class)) {
                    map.put(BatchUtils.toStr(fields[i].getName()), BatchConstants.DEFAULT_IGNORE);
                    continue;
                }

                if (fields[i].isAnnotationPresent(BatchId.class)) {
                    BatchId batchId = fields[i].getAnnotation(BatchId.class);
                    String id = batchId.value();
                    int type = batchId.type().getKey();

                    if (type != BatchIdEnum.AUTO.getKey()) {
                        map.put(BatchUtils.toStr(id), batchId.type().getValue());
                        BatchUtils.appends(sb, id, this.mark, isStart);
                    }
                    continue;
                }

                if (fields[i].isAnnotationPresent(BatchColumns.class)) {
                    BatchColumns batchColumns = fields[i].getAnnotation(BatchColumns.class);
                    map.put(BatchUtils.toStr(batchColumns.value()), BatchConstants.DEFAULT_VALUE);
                    BatchUtils.appends(sb, batchColumns.value(), this.mark, isStart);
                    continue;
                }

                if (fields[i].isAnnotationPresent(BatchFill.class)) {
                    BatchFill batchFill = fields[i].getAnnotation(BatchFill.class);
                    if (batchFill.insert()) {
                        String name = batchFill.value();
                        Map<String, HotPot> beanMap = (Map)BatchBean.getBean(BatchSqlEnum.INSERT_LIST.getMethod());
                        HotPot hotPot = beanMap.get(name);

                        Class bean = hotPot.getClazz();
                        String method = hotPot.getMethod();
                        if (bean != null && !StringUtils.isEmpty(method)) {
                            Method md = bean.getMethod(method, null);
                            map.put(BatchUtils.toStr(name), md.invoke(bean, null));
                        } else {
                            map.put(BatchUtils.toStr(name), hotPot.getVal());
                        }
                        BatchUtils.appends(sb, name, this.mark, isStart);
                        continue;
                    }
                }

                if (fields[i].isAnnotationPresent(BatchLogic.class)) {
                    BatchLogic batchLogic = fields[i].getAnnotation(BatchLogic.class);
                    map.put(BatchUtils.toStr(batchLogic.value()), batchLogic.before());
                    BatchUtils.appends(sb, batchLogic.value(), this.mark, isStart);
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        String values = this.getLatterHalf(list, map);
        return String.format(BatchSqlEnum.INSERT_LIST.getSql(), tableName, sb.toString(), values);
    }

    /**
     * 获取后半部分的SQL字符串语句
     *
     * @param list 数据集合
     * @param map  字段名、字段名所对应的值类型
     * @return SQL字符串语句
     */
    private String getLatterHalf(List list, Map<String, Object> map) {
        StringBuilder builder = new StringBuilder();
        int size = list.size();
        int offset = 0;

        try {
            for (int k = 0; k < size; k++) {
                boolean isEnd = k == 0;
                Field[] fields = super.getField(list.get(k).getClass());
                BatchUtils.appends(builder, BatchConstants.LEFT_PARENTHESIS, this.mark, isEnd);

                int len = fields.length;
                for (int i = 0; i < len; i++) {
                    boolean isLast = i == offset;
                    fields[i].setAccessible(true);

                    if (BatchUtils.isStatic(fields[i])) {
                        if (i == 0 && offset == 0) {
                            offset = 1;
                        }
                        continue;
                    }

                    Object object = map.get(fields[i].getName());
                    if (object == null) {
                        continue;
                    }

                    String name = object.toString();
                    if (BatchConstants.DEFAULT_IGNORE.equals(name)) {
                        continue;
                    }
                    Object obj = this.setVal(name);

                    if (obj != null) {
                        BatchUtils.appends(builder, BatchUtils.getValue(fields[i].getType(), obj), this.mark, isLast);
                        continue;
                    }
                    BatchUtils.appends(builder, BatchUtils.getValue(fields[i].getType(), fields[i].get(list.get(k))), this.mark, isLast);
                }
                BatchUtils.appends(builder, BatchConstants.RIGHT_PARENTHESIS, null, false);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * 不同的类型对应不同的取值
     * 若为空，代表用户自行输入
     *
     * @param type 属性类型
     */
    private Object setVal(String type) {
        if (BatchIdEnum.INPUT.getValue().equals(type)) {
            return null;
        }

        if (BatchIdEnum.ASSIGN_ID.getValue().equals(type)) {
            return BatchSnow.genId();
        }

        if (BatchIdEnum.ASSIGN_UUID.getValue().equals(type)) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }

        if (!StringUtils.isEmpty(type) && !BatchConstants.DEFAULT_VALUE.equals(type)) {
            return type;
        }
        return null;
    }
}
