package com.huoguo.mybatisplus.batch;


import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.huoguo.mybatisplus.batch.annotation.TableId;
import com.huoguo.mybatisplus.batch.annotation.TableName;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.User;
import com.huoguo.mybatisplus.batch.enums.IdType;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;
import com.huoguo.mybatisplus.batch.factory.StatusFactory;
import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;
import com.huoguo.mybatisplus.batch.util.CollectionUtils;
import lombok.SneakyThrows;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/24 21:32
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        List<User> list = new ArrayList<>();
        list.add(new User().setId(1L).setName("张三").setSex(1));
        list.add(new User().setId(2L).setName("李四").setSex(2));
        list.add(new User().setId(3L).setName("王五").setSex(3));

        // 这里需要拆分下list，否则报sql超长
        test1(list);
    }

    @SneakyThrows
    public static void test1(List<?> list) {
        Class<?> clazz = list.get(DefaultConstants.DEFAULT_INDEX_VALUE).getClass();
        // 1、判断是否有表名注解
        if (clazz.isAnnotationPresent(TableId.class)) {
            throw new RuntimeException("The ORM relational mapping object cannot be resolved");
        }
        // 2、获取表名
        String tableName = clazz.getAnnotation(TableName.class).value();
        // 3、获取字段
        Field[] fields = clazz.getDeclaredFields();
        // 定义主键ID集合
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(2);
        // 获取列名
        // String column =
        String column = Arrays.stream(fields).filter(item -> {
            // 获取私有属性权限
            item.setAccessible(true);
            // 判断是否有主键ID注解
            if (item.isAnnotationPresent(TableId.class)) {
                TableId tableId = item.getAnnotation(TableId.class);

                String id = tableId.value();
                int idType = tableId.type().getKey();

                if ("".equals(id)) {
                    throw new RuntimeException("The column name is not defined");
                }

                map.put(DefaultConstants.DEFAULT_ID_NAME, id);
                map.put(DefaultConstants.DEFAULT_ID_TYPE, idType);

                if (idType == IdType.AUTO.getKey()) {
                    return !item.getName().equals(id);
                }
            }
            return true;
        }).map(parameter -> parameter.getName()).collect(Collectors.joining(", "));


        String values = "";
        if (!map.isEmpty()) {
            // 向上转型
            int type = (int)map.get(DefaultConstants.DEFAULT_ID_TYPE);
            // 这个值
            String name = (String)map.get(DefaultConstants.DEFAULT_ID_NAME);

            StitchingSqlService stitchingSqlService = StatusFactory.getServicePath(type);
            values = stitchingSqlService.getSqlString(column, CollectionUtils.toMap(list));
        }

        String sql = String.format(SqlMethod.INSERT_LIST.getSql(), tableName, column, values);
        System.out.println(sql);
        // 这里注入SqlSeesion来操作

    }
}
