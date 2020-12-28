package com.huoguo.mybatisplus.batch.template.child;

import com.huoguo.mybatisplus.batch.annotation.TableColumns;
import com.huoguo.mybatisplus.batch.annotation.TableDate;
import com.huoguo.mybatisplus.batch.annotation.TableId;
import com.huoguo.mybatisplus.batch.annotation.TableLogic;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.enums.IdType;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;
import com.huoguo.mybatisplus.batch.factory.StatusFactory;
import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName InsertChildTemplate
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/28 9:15
 * @Version 1.0
 */
public class InsertChildTemplate extends AbstractTemplate {

    @Override
    protected String getSql(List<?> list, Field[] fields, String tableName) {
        StringBuilder stringBuilder = new StringBuilder();
        int idType = 0, len = fields.length;

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(16);

        String id = "";

        for (int i = 0; i < len; i++) {
            fields[i].setAccessible(true);

            if (fields[i].isAnnotationPresent(TableId.class)) {
                TableId tableId = fields[i].getAnnotation(TableId.class);

                id = tableId.value();
                idType = tableId.type().getKey();

                if ("".equals(id)) {
                    throw new RuntimeException("The column name is not defined");
                }

                if (idType != IdType.AUTO.getKey()) {
                    stringBuilder.append(id).append(DefaultConstants.DEFAULT_COMMA);
                }

            } else if (fields[i].isAnnotationPresent(TableDate.class)) {
                TableDate tableDate = fields[i].getAnnotation(TableDate.class);
                map.put("date_column", tableDate.value());
                map.put("date_value", tableDate.type().getValue());

            } else if (fields[i].isAnnotationPresent(TableDate.class)) {
                TableLogic tableLogic = fields[i].getAnnotation(TableLogic.class);
                map.put("logic_column", tableLogic.value());
                map.put("logic_value", tableLogic.before());

            } else {
                if (!fields[i].isAnnotationPresent(TableColumns.class)) {
                    throw new RuntimeException("The columns name is not defined");
                }

                TableColumns tableColumns = fields[i].getAnnotation(TableColumns.class);
                stringBuilder.append(tableColumns.value());

                if (i != len - 1) {
                    stringBuilder.append(DefaultConstants.DEFAULT_COMMA);
                }
            }
        }
        StitchingSqlService stitchingSqlService = StatusFactory.getServicePath(idType);
        String values = stitchingSqlService.getSqlString(list, fields, id);

        return String.format(SqlMethod.INSERT_LIST.getSql(), tableName, stringBuilder.toString(), values);
    }
}
