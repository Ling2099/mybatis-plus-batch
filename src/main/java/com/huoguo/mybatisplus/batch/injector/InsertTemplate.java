package com.huoguo.mybatisplus.batch.injector;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;
import com.huoguo.mybatisplus.batch.template.AbstractMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName InsertTemplate
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/14 9:40
 * @Version 1.0
 */
public class InsertTemplate extends AbstractMethod {

    /**
     * @Author LZH
     * @Description 暂定位拼接SQL
     * @Date 2020/12/14 9:52
     * @Param tableInfo MybatisPlus中的表信息对象
     * @Param entityList 来自客户端的数据集合
     * @return void
     **/
    @Override
    protected <T> void spliceSql(TableInfo tableInfo, List<T> entityList) {
        // 获取枚举类中的INSERT值
        SqlMethod sqlMethod = SqlMethod.INSERT_LIST;
        // 获取列名
        String column = tableInfo.getFieldList().stream().map(item -> item.getEl()).collect(Collectors.joining(","));

        // 这里要解析数据了


        // 字符串的替换 --> 表名、列名、数据
        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), column, "这里替换的是数据");

        super.command(sql);
    }
}
