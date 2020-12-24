package com.huoguo.mybatisplus.batch.test;


import com.huoguo.mybatisplus.batch.annotation.TableId;
import com.huoguo.mybatisplus.batch.annotation.TableName;
import com.huoguo.mybatisplus.batch.entity.User;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/24 21:32
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        // 获取类上的注解
//        TableName tableName = User.class.getAnnotation(TableName.class);
//
//        try {
//            Field id = User.class.getDeclaredField("id");
//            id.setAccessible(true);
//
//            // 判断该字段上是否有注解
//            boolean is = id.isAnnotationPresent(TableId.class);
//            System.out.println(is);
//
//            TableId tableId = id.getAnnotation(TableId.class);
//            System.out.println(tableId);
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("表名：" + tableName.value());
        List<User> list = new ArrayList<>();
        list.add(new User().setId(1L).setName("张三").setSex(1));

        test1(list);
    }

    public static void test1(List<?> list) {
        Class<?> clazz = list.get(0).getClass();
        // 1、判断是否有表名注解
        if (clazz.isAnnotationPresent(TableId.class)) {
            throw new RuntimeException("The ORM relational mapping object cannot be resolved");
        }
        // 2、获取表名
        String tableName = clazz.getAnnotation(TableName.class).value();
        System.out.println("表名：" + tableName);

        // 3、获取字段
        Field[] fields = clazz.getDeclaredFields();
        Arrays.asList(fields).stream().forEach(item -> {
            // 获取私有属性权限
            item.setAccessible(true);

            // 判断有主键注解的字段
            if (item.isAnnotationPresent(TableId.class)) {
                TableId tableId = item.getAnnotation(TableId.class);
                System.out.println("主键是：" + tableId.value() + "  ,类型是：" + tableId.type().name());
            }
        });
    }
}
