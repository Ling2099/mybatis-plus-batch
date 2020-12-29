package com.huoguo.mybatisplus.batch.strategy.impl;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;
import com.huoguo.mybatisplus.batch.util.BatchUtils;
import com.huoguo.mybatisplus.batch.util.SnowflakeUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName AssignUuidServiceImpl
 * @Description UUID主键
 * @Author LZH
 * @Date 2020/12/25 11:49
 * @Version 1.0
 */
public class AssignUuidServiceImpl implements StitchingSqlService {

    @Override
    public String getSqlString(List<?> list, String id, ConcurrentHashMap<String, Object> map) {
        StringBuilder stringBuilder = new StringBuilder();

        String dateColumn = map.get("date_column").toString();
        String dateValue = map.get("date_value").toString();

        String logicColumn = map.get("logic_column").toString();
        String logicValue = map.get("logic_value").toString();

        int size = list.size();

        try {
            for (int k = 0; k < size; k++) {
                Field[] field = list.get(k).getClass().getDeclaredFields();
                int len = field.length;

                stringBuilder.append(DefaultConstants.LEFT_PARENTHESIS);

                for (int i = 0; i < len; i++) {
                    field[i].setAccessible(true);

                    String name = field[i].getName();
                    if (name.equals(id)) {
                        stringBuilder.append("'" + UUID.randomUUID().toString().replaceAll("-","") + "'").append(DefaultConstants.DEFAULT_COMMA);
                        continue;
                    }

                    Class<?> type = field[i].getType();
                    Object value = field[i].get(list.get(k));
                    if (dateColumn.equals(name)) {
                        if (!"".equals(dateValue)) {
                            stringBuilder.append(dateValue);
                        } else {
                            stringBuilder.append(BatchUtils.getTypeValue(type, value));
                        }
                    } else if (logicColumn.equals(name)) {
                        stringBuilder.append(logicValue);
                    } else {
                        stringBuilder.append(BatchUtils.getTypeValue(type, value));
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
}
