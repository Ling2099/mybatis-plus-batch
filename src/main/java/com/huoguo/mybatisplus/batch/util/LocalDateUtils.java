package com.huoguo.mybatisplus.batch.util;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName LocalDateUtils
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/28 17:09
 * @Version 1.0
 */
public class LocalDateUtils {

    /**
     * @Author LZH
     * @Description 获取当前年月日
     * @Date 2020/12/28 17:52
     * @Param []
     * @return java.lang.String
     **/
    public static String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultConstants.YYYY_MM_DD);
        LocalDate localDate = LocalDate.now();
        return localDate.format(formatter);
    }

    /**
     * @Author LZH
     * @Description 获取当前时分秒
     * @Date 2020/12/28 17:52
     * @Param []
     * @return java.lang.String
     **/
    public static String getTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DefaultConstants.HH_MM_SS);
        LocalTime localTime = LocalTime.now();
        return localTime.format(formatter);
    }
}
