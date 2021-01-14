package com.huoguo.mybatisplus.batch.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
 *
 * @author Lizhenghuang
 */
public final class BatchUtils {

    /**
     * 转换数据库与实体类映射字段
     * @param str 数据库字段
     * @return 实体类属性
     */
    public static String toStr(String str) {
        StringBuilder builder = new StringBuilder();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(i);
            if ("_".equals(ch + "")) {
                i = i + 1;
                String up = (str.charAt(i) + "").toUpperCase();
                builder.append(up);
                continue;
            }
            builder.append(ch);
        }
        return builder.toString();
    }

    /**
     * 用于辨别属性类型，返回合适的类型值
     *
     * @param type  属性类型
     * @param value 属性值
     * @return 合适的属性值
     */
    public static Object getValue(Class<?> type, Object value) {
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
                return "null";
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
                return "null";
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + formatter.format(value) + "'";
        } else {
            return type.cast(value);
        }
    }
}
