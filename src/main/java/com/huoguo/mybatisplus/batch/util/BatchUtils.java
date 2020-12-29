package com.huoguo.mybatisplus.batch.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName BatchUtils
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/29 13:43
 * @Version 1.0
 */
public final class BatchUtils {

    public static Object getTypeValue(Class<?> type, Object value) {
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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + formatter.format(value) + "'";
        } else {
            return type.cast(value);
        }
    }
}
