package com.huoguo.mybatisplus.batch.service.impl;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.huoguo.mybatisplus.batch.entity.Test;
import com.huoguo.mybatisplus.batch.mapper.BaseMapper;
import com.huoguo.mybatisplus.batch.service.BatchService;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @ClassName BatchServiceImpl
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/24 16:40
 * @Version 1.0
 */
public class BatchServiceImpl implements BatchService {


    @Override
    public Boolean insert(List<?> list) {
        Class<?> calzz = list.get(0).getClass();
        TableInfo tableInfo = TableInfoHelper.getTableInfo(calzz);
        // 表名
        System.out.println(tableInfo.getTableName());
        // 列名
        String defaultStr = FieldFill.DEFAULT.name();
        String columns = tableInfo.getFieldList().stream().map(item -> item.getEl()).collect(Collectors.joining(", "));
        System.out.println(columns);


        for (int i = 0; i < list.size(); i ++) {
            Field[] fields = list.get(i).getClass().getDeclaredFields();
            try {
                for (Field field : fields) {
                    field.setAccessible(true);
                    String name = field.getName();
                    Object value = field.get(list.get(i));
                    System.out.println("ket=" + name + "  value=" + value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        System.out.println(list.get(0).getClass().getSuperclass().getSuperclass());




        BaseMapper baseMapper = (BaseMapper)Proxy.newProxyInstance(BaseMapper.class.getClassLoader(), new Class<?>[]{BaseMapper.class}, (proxy, method, args) -> {

            ConcurrentHashMap<String, Object> map = buildMethodArgs(tableInfo, method, args);
            return null;
        });
        baseMapper.insert(list);
        return null;
    }


    private static ConcurrentHashMap<String, Object> buildMethodArgs(TableInfo tableInfo, Method method, Object[] args) {
        Parameter[] parameters = method.getParameters();

        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>(4);

        // System.out.println(method.getParameterTypes());
        // System.out.println(method.getTypeParameters());

        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            // System.out.println(name);
            map.put(name, args[0]);
            // System.out.println(args[0]);
        });

        return map;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.setName("测试1");
        test.setAge(10);
        test.setCreator("张三");
        test.setDeptId(10L);
        test.setCreatorId(1L);

        Test test2 = new Test();
        test2.setName("测试2");
        test2.setAge(11);
        test.setCreator("张三");
        test.setDeptId(10L);
        test.setCreatorId(1L);

        List<Test> list = new ArrayList<>();
        list.add(test);
        list.add(test2);
        BatchService batchService = new BatchServiceImpl();
        batchService.insert(list);

    }
}
