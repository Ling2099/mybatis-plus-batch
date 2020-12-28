package com.huoguo.mybatisplus.batch.strategy.impl;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName AutoServiceImpl
 * @Description 主键自增长实现类
 * @Author LZH
 * @Date 2020/12/25 11:35
 * @Version 1.0
 */
public class AutoServiceImpl implements StitchingSqlService {

    @Override
    public String getSqlString(List<?> list, Field[] fields, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        int size = list.size();

        try {
            for (int k = 0; k < size; k++) {
                Field[] field1 = list.get(k).getClass().getDeclaredFields();
                int len = field1.length;

                stringBuilder.append(DefaultConstants.LEFT_PARENTHESIS);

                for (int i = 0; i < len; i++) {
                    if (field1[i].getName().equals(id)) {
                        continue;
                    }
                    field1[i].setAccessible(true);

                    stringBuilder.append(getTypeValue(field1[i].getType(), field1[i].get(list.get(k))));
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

    private static Object getTypeValue(Class<?> type, Object value) {
        if (type == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return Integer.parseInt(value.toString());
        } else if (type == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (type == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (type == double.class) {
            if (null == value) {
                return 0;
            }
            return Double.parseDouble(value.toString());
        } else if (type == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (type == String.class) {
            if (null == value) {
                return "";
            }
            return "'" + value + "'";
        } else if (type == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (type == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else if (type == Date.class) {
            if (null == value) {
                return value;
            }
            return null;
        } else {
            return type.cast(value);
        }
    }
}
