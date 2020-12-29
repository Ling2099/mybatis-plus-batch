package com.huoguo.mybatisplus.batch.template;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.huoguo.mybatisplus.batch.annotation.TableName;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @ClassName: AbstractTemplate
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/27 12:39
 * @Version: 1.0
 */
public abstract class AbstractTemplate {

    public Boolean bacth(List<?> list, int size) {
        return splitList(list, size);
    }

    private Boolean splitList(List<?> list, int size) {
        int total = list.size();

        if(total <= size){
            return doSth(list);
        }

        int limit = total / size;
        int residue = total % size;
        Boolean mark = false;
        for(int i = 0 ; i < limit; i++){
            mark = doSth(list.subList(i * size, size * (i + 1)));
        }
        if(residue > 0){
            mark = doSth(list.subList(limit * size, total));
        }
        return mark;
    }

    private Boolean doSth(List<?> list) {
        Class<?> clazz = getClazz(list);
        String tableName = getTableName(clazz);
        Field[] fields = clazz.getDeclaredFields();
        String sql = getSql(list, fields, tableName);
        return execute(sql, clazz);
    }

    private Class<?> getClazz(List<?> list) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("The current collection is empty");
        }
        return list.get(DefaultConstants.DEFAULT_INDEX_VALUE).getClass();
    }

    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(TableName.class)) {
            throw new RuntimeException("The ORM relational mapping object cannot be resolved");
        }
        return clazz.getAnnotation(TableName.class).value();
    }

    protected abstract String getSql(List<?> list, Field[] fields, String tableName);

    private Boolean execute(String sql, Class<?> clazz) {
        SqlSession sqlSession = SqlHelper.sqlSessionBatch(clazz);
        Configuration configuration = sqlSession.getConfiguration();
        Connection connection = null;
        Statement stmt = null;
        try {
            connection = configuration.getEnvironment().getDataSource().getConnection();
            stmt = connection.createStatement();
            return stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
