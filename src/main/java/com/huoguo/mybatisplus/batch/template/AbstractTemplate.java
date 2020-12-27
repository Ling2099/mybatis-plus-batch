package com.huoguo.mybatisplus.batch.template;

import com.huoguo.mybatisplus.batch.annotation.TableId;
import com.huoguo.mybatisplus.batch.annotation.TableName;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.enums.IdType;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: AbstractTemplate
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/27 12:39
 * @Version: 1.0
 */
public abstract class AbstractTemplate {

    protected void doBacth(List<?> list) {
        Class<?> clazz = getClazz(list);
        Field[] fields = clazz.getDeclaredFields();

        String tableName = getTableName(clazz);
        String colums = getColumns(fields);

    }

    private Class<?> getClazz(List<?> list) {
        return list.get(DefaultConstants.DEFAULT_INDEX_VALUE).getClass();
    }

    private String getTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(TableId.class)) {
            throw new RuntimeException("The ORM relational mapping object cannot be resolved");
        }
        return clazz.getAnnotation(TableName.class).value();
    }

    protected abstract String getSql();

    private String getColumns(Field[] fields) {
        return Arrays.stream(fields).filter(item -> {
            item.setAccessible(true);

            if (item.isAnnotationPresent(TableId.class)) {
                TableId tableId = item.getAnnotation(TableId.class);

                String id = tableId.value();
                int idType = tableId.type().getKey();

                if ("".equals(id)) {
                    throw new RuntimeException("The column name is not defined");
                }

                if (idType == IdType.AUTO.getKey()) {
                    return !item.getName().equals(id);
                }
            }
            return true;
        }).map(Field::getName).collect(Collectors.joining(", "));
    }
}
