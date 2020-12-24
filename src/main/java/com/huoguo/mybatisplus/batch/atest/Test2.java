package com.huoguo.mybatisplus.batch.atest;

import org.apache.ibatis.annotations.Insert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @ClassName Test2
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/24 16:22
 * @Version 1.0
 */

interface MyMapper {

    @Insert("insert into #{tableName} (#{columns}) values #{val}")
    Boolean insert(List<User> list);
}

public class Test2 {

    public static void main(String[] args) {
        MyMapper myMapper = (MyMapper)Proxy.newProxyInstance(Test2.class.getClassLoader(), new Class<?>[]{MyMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Insert annotation = method.getAnnotation(Insert.class);
                Map<String, Object> map = buildMethodArgs(method, args);
                if (annotation != null) {
                    String[] value = annotation.value();
                    System.out.println(value[0]);
                }
                return null;
            }
        });

        List<User> list = new ArrayList<>();
        list.add(new User().setId(1L).setName("张三").setSex(1));
        list.add(new User().setId(2L).setName("李四").setSex(0));
        myMapper.insert(list);
    }

    public static Map<String, Object> buildMethodArgs(Method method, Object[] args) {
        Map<String, Object> map = new HashMap<>(4);
        Parameter[] parameters = method.getParameters();



        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            // System.out.println(name);
            // System.out.println(parameter.getType());
            map.put(name, args[0]);
        });
        return map;
    }
}
