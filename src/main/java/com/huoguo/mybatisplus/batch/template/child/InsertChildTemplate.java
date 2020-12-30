package com.huoguo.mybatisplus.batch.template.child;

import com.huoguo.mybatisplus.batch.annotation.TableColumns;
import com.huoguo.mybatisplus.batch.annotation.TableDate;
import com.huoguo.mybatisplus.batch.annotation.TableId;
import com.huoguo.mybatisplus.batch.annotation.TableLogic;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.enums.IdType;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.util.BatchUtils;
import com.huoguo.mybatisplus.batch.util.SnowflakeUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
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
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(16);
        StringBuilder stringBuilder = new StringBuilder();
        int idType = 0, len = fields.length;
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
                map.put("date_column", fields[i].getName());
                map.put("date_value", tableDate.type().getValue());
                stringBuilder.append(tableDate.value());

            } else if (fields[i].isAnnotationPresent(TableLogic.class)) {
                TableLogic tableLogic = fields[i].getAnnotation(TableLogic.class);
                map.put("logic_column", fields[i].getName());
                map.put("logic_value", tableLogic.before());
                stringBuilder.append(tableLogic.value());

            } else {
                if (!fields[i].isAnnotationPresent(TableColumns.class)) {
                    throw new RuntimeException("The columns name is not defined");
                }

                TableColumns tableColumns = fields[i].getAnnotation(TableColumns.class);
                stringBuilder.append(tableColumns.value());
            }

            if (i != 0 && i != len - 1) {
                stringBuilder.append(DefaultConstants.DEFAULT_COMMA);
            }
        }
        String values = this.getValue(list, idType, id, map);

        return String.format(SqlMethod.INSERT_LIST.getSql(), tableName, stringBuilder.toString(), values);
    }

    private String getValue(List<?> list, int type, String id, ConcurrentHashMap<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();

        String dateColumn = map.get(DefaultConstants.DATE_COLUMN).toString();
        String dateValue = map.get(DefaultConstants.DATE_VALUE).toString();

        String logicColumn = map.get(DefaultConstants.LOGIC_COLUMN).toString();
        String logicValue = map.get(DefaultConstants.LOGIC_VALUE).toString();

        int size = list.size();

        try {
            for (int k = 0; k < size; k++) {
                Field[] field = list.get(k).getClass().getDeclaredFields();
                int len = field.length;

                stringBuilder.append(DefaultConstants.LEFT_PARENTHESIS);

                for (int i = 0; i < len; i++) {
                    field[i].setAccessible(true);

                    String name = field[i].getName();
                    Class<?> clazzType = field[i].getType();
                    Object value = field[i].get(list.get(k));

                    if (name.equals(id)) {
                        this.setValue(type, stringBuilder, clazzType, value);
                        continue;
                    } else if (dateColumn.equals(name)) {
                        if (!"".equals(dateValue)) {
                            stringBuilder.append(dateValue);
                        } else {
                            stringBuilder.append(BatchUtils.getTypeValue(clazzType, value));
                        }
                    } else if (logicColumn.equals(name)) {
                        stringBuilder.append(logicValue);
                    } else {
                        stringBuilder.append(BatchUtils.getTypeValue(clazzType, value));
                    }

                    if (i != len - 1) {
                        stringBuilder.append(DefaultConstants.DEFAULT_COMMA);
                    }
                }
                stringBuilder.append(DefaultConstants.RIGHT_PARENTHESIS);

                if (k != size - 1) {
                    stringBuilder.append(DefaultConstants.DEFAULT_COMMA);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private void setValue(int type, StringBuilder stringBuilder, Class<?> clazzType, Object value) {
        switch (type) {
            case 0 : break;
            case 1 : stringBuilder.append(BatchUtils.getTypeValue(clazzType, value)); break;
            case 2 : stringBuilder.append(SnowflakeUtils.genId()); break;
            case 3 : stringBuilder.append("'" + UUID.randomUUID().toString().replaceAll("-","") + "'"); break;
            default:
        }

        if (type != 0) {
            stringBuilder.append(DefaultConstants.DEFAULT_COMMA);
        }
    }
}
