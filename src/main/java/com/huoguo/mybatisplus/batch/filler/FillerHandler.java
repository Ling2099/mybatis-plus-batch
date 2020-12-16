package com.huoguo.mybatisplus.batch.filler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;

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

    public static <T> void handleId(TableInfo tableInfo, List<T> entityList) throws Exception {
        // 获取枚举类中的INSERT值
        SqlMethod sqlMethod = SqlMethod.INSERT_LIST;

        switch (tableInfo.getIdType().getKey()) {
            case DefaultConstants.AUTO :
                handleAutoId(tableInfo, sqlMethod.getSql(), entityList);

                break;
            default :
                System.out.println(222);
                break;
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
                .filter(item -> defaultStr.equals(item.getFieldFill().name()))
                .map(item -> item.getEl())
                .collect(Collectors.joining(", "));

        Class<?> clazz = tableInfo.getEntityType();
        Object obj = clazz.newInstance();



        StringBuilder builder = new StringBuilder();

        entityList.stream().forEach(item -> {
            Map map = JSONObject.parseObject(JSONObject.toJSONString(item), Map.class);

            // builder.append("(" + v + "),");
            builder.append("(");
            map.forEach((k, v) -> {
                builder.append(v + ",");

            });
            builder.append(")");
        });

        System.out.println(builder);

        String sql = String.format(sqlMethod, tableInfo.getTableName(), column, "这里替换的是数据");
        System.out.println(sql);
        return sql;
    }
}
