package com.huoguo.mybatisplus.batch.template.child;

import com.huoguo.mybatisplus.batch.annotation.*;
import com.huoguo.mybatisplus.batch.constant.BatchConstants;
import com.huoguo.mybatisplus.batch.enums.BatchIdEnum;
import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.service.impl.BatchServiceImpl;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.test.User;
import com.huoguo.mybatisplus.batch.util.BatchBean;
import com.huoguo.mybatisplus.batch.util.BatchSnow;
import com.huoguo.mybatisplus.batch.util.BatchUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 子类实现父类的抽象方法，拼接出可执行的SQL语句
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
     * @return 可执行的SQL字符串语句
     */
    @Override
    protected String getSql(List list, Field[] fields, String tableName) {
        int len = fields.length;
        Map<String, Object> map = new ConcurrentHashMap<>(len);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            boolean isStart = i == 0;
            fields[i].setAccessible(true);

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
                    this.appends(sb, id, this.mark, isStart);
                }
                continue;
            }

            if (fields[i].isAnnotationPresent(BatchColumns.class)) {
                BatchColumns batchColumns = fields[i].getAnnotation(BatchColumns.class);
                map.put(BatchUtils.toStr(batchColumns.value()), BatchConstants.DEFAULT_VALUE);
                this.appends(sb, batchColumns.value(), this.mark, isStart);
                continue;
            }

            if (fields[i].isAnnotationPresent(BatchFill.class)) {
                BatchFill batchFill = fields[i].getAnnotation(BatchFill.class);
                if (batchFill.insert()) {
                    String name = batchFill.value();
                    Map<String, Object> beanMap = (Map)BatchBean.getBean(BatchConstants.FILL_NAME_INSERT);
                    map.put(BatchUtils.toStr(name), beanMap.get(batchFill.value()));
                    this.appends(sb, name, this.mark, isStart);
                    continue;
                }
            }

            if (fields[i].isAnnotationPresent(BatchLogic.class)) {
                BatchLogic batchLogic = fields[i].getAnnotation(BatchLogic.class);
                map.put(BatchUtils.toStr(batchLogic.value()), batchLogic.before());
                this.appends(sb, batchLogic.value(), this.mark, isStart);
                continue;
            }
        }
        System.out.println(sb.toString());
        String values = this.getLatterHalf(list, map);
        System.out.println(values);
        // return String.format(BatchSqlEnum.INSERT_LIST.getSql(), tableName, sb.toString(), values);
        return null;
    }

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();

        User user = new User();
        user.setId(1L);
        // user.setName(null);
        user.setAge(15);

        list.add(user);

        User user1 = new User();
        user1.setId(1L);
        user1.setName("张三");
        user1.setAge(15);
        list.add(user1);

        BatchService batchService = new BatchServiceImpl();
        batchService.insertBatch(list);
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

        try {
            for (int k = 0; k < size; k++) {
                boolean isEnd = k == 0;
                Field[] fields = list.get(k).getClass().getDeclaredFields();
                this.appends(builder, BatchConstants.LEFT_PARENTHESIS, this.mark, isEnd);

                int len = fields.length;
                for (int i = 0; i < len; i++) {
                    boolean isLast = i == 0;
                    fields[i].setAccessible(true);

                    String name = map.get(fields[i].getName()).toString();
                    if (BatchConstants.DEFAULT_IGNORE.equals(name)) {
                        continue;
                    }
                    Object obj = this.setVal(name);

                    if (obj != null) {
                        this.appends(builder, BatchUtils.getValue(fields[i].getType(), obj), this.mark, isLast);
                        continue;
                    }
                    this.appends(builder, BatchUtils.getValue(fields[i].getType(), fields[i].get(list.get(k))), this.mark, isLast);
                }
                this.appends(builder, BatchConstants.RIGHT_PARENTHESIS, null, false);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * StringBuilder拼接SQL字符串
     * @param sb StringBuilder
     * @param val 字段、值或括号
     * @param str 逗号
     * @param isLast 是否为最后一行数据
     */
    private void appends(StringBuilder sb, Object val, String str, boolean isLast) {
        if (!isLast) {
            if (!StringUtils.isEmpty(str)) {
                sb.append(str);
            }
        }
        if (!StringUtils.isEmpty(val)) {
            sb.append(val);
        }
    }

    /**
     * 不同的类型对应不同的取值
     * 若为空，代表用户自行输入
     *
     * @param type 属性类型
     */
    private Object setVal(String type) {
        if (BatchConstants.DEFAULT_INPUT.equals(type)) {
            return null;
        }

        if (BatchConstants.DEFAULT_ASSIGN_ID.equals(type)) {
            return BatchSnow.genId();
        }

        if (BatchConstants.DEFAULT_ASSIGN_UUID.equals(type)) {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }

        if (!StringUtils.isEmpty(type) && !BatchConstants.DEFAULT_VALUE.equals(type)) {
            return type;
        }
        return null;
    }
}
