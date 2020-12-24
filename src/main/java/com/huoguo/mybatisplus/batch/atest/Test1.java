package com.huoguo.mybatisplus.batch.atest;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Test1
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/24 13:58
 * @Version 1.0
 */

interface UserMapper {

    @Select("select * from user where id = #{id} and name = #{name}")
    List<User> selectUserList(Long id, String name);

}

public class Test1 {

    /**
     * @Author LZH
     * @Description 主方法 执行代理
     * @Table
     * @Date 2020/12/24 14:36
     * @Param [args]
     * @return void
     **/
    public static void main(String[] args) {
        UserMapper userMapper = (UserMapper)Proxy.newProxyInstance(Test1.class.getClassLoader(), new Class<?>[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Select annotation = method.getAnnotation(Select.class);
                Map<String, Object> map = buildMethodArgs(method, args);
                if (annotation != null) {
                    String[] value = annotation.value();
                    String result =  getSql(value[0].toString(), map);
                    System.out.println(result);
                    // System.out.println(Arrays.toString(value));
                }
                // System.out.println(method.getReturnType());
                // System.out.println(method.getGenericReturnType());
                return null;
            }
        });
        userMapper.selectUserList(1L,"test");
    }

    /**
     * @Author LZH
     * @Description 解析SQL
     * @Table
     * @Date 2020/12/24 14:37
     * @Param [sql, map]
     * @return java.lang.String
     **/
    public static String getSql(String sql, Map<String, Object> map) {
        String parseSql = "";
        StringBuilder stringBuilder = new StringBuilder();
        int len = sql.length();
        for (int i = 0; i < len; i ++) {
            char a = sql.charAt(i);
            if (a == '#') {
                int nextIndex = i + 1;
                char nextSql = sql.charAt(nextIndex);
                if (nextSql != '{') {
                    throw new RuntimeException("这里不是{");
                }

                StringBuilder args = new StringBuilder();
                i = parsePartSql(args, sql, nextIndex);
                String argName = args.toString();
                stringBuilder.append(map.get(argName).toString());
               continue;
            }
            stringBuilder.append(a);
        }
        return stringBuilder.toString();
    }

    /**
     * @Author LZH
     * @Description 获取 #{} 的下一个坐标
     * @Table
     * @Date 2020/12/24 16:10
     * @Param [args, sql, nextIndex]
     * @return int
     **/
    private static int parsePartSql(StringBuilder args, String sql, int nextIndex) {
        nextIndex ++;
        int len = sql.length();
        for (; nextIndex < len; nextIndex ++) {
            char a = sql.charAt(nextIndex);
            if (a != '}') {
                args.append(a);
                continue;
            }

            if (a == '}') {
                return nextIndex;
            }
        }
        throw new RuntimeException("没有}");
    }

    /**
     * @Author LZH
     * @Description 解析参数
     * @Table
     * @Date 2020/12/24 14:36
     * @Param [method, args]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public static Map<String, Object> buildMethodArgs(Method method, Object[] args) {
        Map<String, Object> map = new HashMap<>(4);
        Parameter[] parameters = method.getParameters();

        // 定义数组（及下标，绕过虚拟机的匿名类）
        int index[] = {0};

        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            // System.out.println(name);
            // System.out.println(parameter.getType());
            map.put(name, args[index[0]]);
            index[0] ++;
        });
        return map;
    }
}
