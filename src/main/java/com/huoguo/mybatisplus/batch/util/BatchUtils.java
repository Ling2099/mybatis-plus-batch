package com.huoguo.mybatisplus.batch.util;

import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * 工具类
 *
 * @author Lizhenghuang
 */
public final class BatchUtils {

    /**
     * 合并数组
     *
     * @param first  第一个数组
     * @param second 第二个数组
     * @param <T>    泛型
     * @return 新的数组
     */
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    /**
     * 在反射时，排除掉序列化ID
     *
     * @param field 当前对象属性
     * @return true代表当前属性为序列化ID
     */
    public static Boolean isStatic(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * 转换数据库与实体类映射字段
     *
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
            return null == value ? 0 : Integer.parseInt(value.toString());
        } else if (type == short.class) {
            return null == value ? 0 : value;
        } else if (type == byte.class) {
            return null == value ? 0 : value;
        } else if (type == double.class) {
            return null == value ? 0 : Double.parseDouble(value.toString());
        } else if (type == long.class) {
            return null == value ? 0 : value;
        } else if (type == Long.class) {
            return null == value ? 0L : value;
        } else if (type == String.class) {
            return null == value ? "null" : "'" + value + "'";
        } else if (type == boolean.class) {
            return null == value ? true : value;
        } else if (type == BigDecimal.class) {
            return null == value ? new BigDecimal(0) : new BigDecimal(value + "");
        } else if (type == Date.class) {
            return null == value ? "null" : "'" + value + "'";
        } else {
            return type.cast(value);
        }
    }

    /**
     * StringBuilder拼接SQL字符串
     *
     * @param sb     StringBuilder
     * @param val    字段、值或括号
     * @param str    逗号
     * @param isLast 是否为最后一行数据
     */
    public static void appends(StringBuilder sb, Object val, String str, boolean isLast) {
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
     * 字符串拼接
     *
     * @param sb      StringBuilder
     * @param objects Object数组
     */
    public static void appends(StringBuilder sb, Object... objects) {
        for (Object obj : objects) {
            sb.append(obj);
        }
    }

    /**
     * 获取UUID
     *
     * @return 字符串
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
