package com.huoguo.mybatisplus.batch.filler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;
import com.mysql.cj.xdevapi.SessionFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @ClassName: FillerHandler
 * @Description: Mybatis Plus 填充数据处理
 * @Author: LZH
 * @Date: 2020/12/15 23:38
 * @Version: 1.0
 */
public final class FillerHandler {

    public static <T> String handleId(TableInfo tableInfo, List<T> entityList) throws Exception {
        // 获取枚举类中的INSERT值
        SqlMethod sqlMethod = SqlMethod.INSERT_LIST;

        switch (tableInfo.getIdType().getKey()) {
            case DefaultConstants.AUTO :
                return handleAutoId(tableInfo, sqlMethod.getSql(), entityList);
            default :
                return "";
        }
    }

    private static <T> String handleAutoId(TableInfo tableInfo, String sqlMethod, List<T> entityList) throws Exception {
        // 逻辑删除的字段信息
        TableFieldInfo tableFieldInfo = tableInfo.getLogicDeleteFieldInfo();
        String delColumn = Optional.ofNullable(tableFieldInfo).map(s -> s.getEl()).get();
        // MybatisPlus自动填充字段之外类型
        String defaultStr = FieldFill.DEFAULT.name();
        // 需要入库的字段
        String column = tableInfo.getFieldList().stream().filter(item -> !item.getEl().equals(delColumn))
                // .filter(item -> defaultStr.equals(item.getFieldFill().name()))
                .map(item -> item.getEl())
                .collect(Collectors.joining(", "));

        Class<?> clazz = tableInfo.getEntityType();
        Object obj = clazz.newInstance();


        StringBuilder builder = new StringBuilder();

        entityList.stream().forEach(item -> {
            Map map = JSONObject.parseObject(JSONObject.toJSONString(item), Map.class);

            builder.append("(");
            map.forEach((k, v) -> {
                builder.append(v + ",");

            });
            builder.append(")");
        });

        String sql = String.format(sqlMethod, tableInfo.getTableName(), column, "('测试1',10),('测试2',11)");



        SqlSession sqlSession = SqlHelper.sqlSessionBatch(tableInfo.getEntityType());
        Configuration configuration = sqlSession.getConfiguration();
        Connection connection = configuration.getEnvironment().getDataSource().getConnection();
        Statement stmt = connection.createStatement();
        // 执行sql
        // boolean falg = stmt.execute(sql);


        


        System.out.println(sql);
        return sql;
    }
}
