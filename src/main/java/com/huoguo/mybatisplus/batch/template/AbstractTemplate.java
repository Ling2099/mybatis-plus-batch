package com.huoguo.mybatisplus.batch.template;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.huoguo.mybatisplus.batch.annotation.BatchName;
import com.huoguo.mybatisplus.batch.annotation.BatchSuper;
import com.huoguo.mybatisplus.batch.constant.BatchConstants;
import com.huoguo.mybatisplus.batch.util.BatchUtils;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 模板模式执行sql字符串的动态拼接与操作数据库
 *
 * @author Lizhenghuang
 */
public abstract class AbstractTemplate {

    /**
     * 模板模式对外提供的方法
     *
     * @param list 数据集合
     * @param size 每次批量操作的数据集合大小
     * @return 是否成功
     */
    public Boolean bacth(List<?> list, int size) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("The current collection is empty");
        }
        return this.splitList(list, size);
    }

    /**
     * 拆分数据集合
     *
     * @param list 数据集合
     * @param size 每次批量操作的数据集合大小
     * @return 是否成功
     */
    private Boolean splitList(List<?> list, int size) {
        int total = list.size();

        if (total <= size) {
            return this.handle(list);
        }

        int limit = total / size;
        int residue = total % size;
        Boolean mark = false;
        for (int i = 0; i < limit; i++) {
            mark = this.handle(list.subList(i * size, size * (i + 1)));
        }
        if (residue > 0) {
            mark = this.handle(list.subList(limit * size, total));
        }
        return mark;
    }

    /**
     * 获取表名、列名、相对应的值，用此来拼接SQL，并执行数据库操作
     *
     * @param list 数据集合
     * @return 是否成功
     */
    private Boolean handle(List<?> list) {
        Class<?> clazz = this.getClazz(list);
        String tableName = this.getTableName(clazz);
        String sql = this.getSql(list, this.getField(clazz), tableName);
        return execute(sql, clazz);
    }

    /**
     * 获取Class类型
     *
     * @param list 数据集合
     * @return 参数中的class
     */
    private Class<?> getClazz(List<?> list) {
        return list.get(BatchConstants.DEFAULT_INDEX_VALUE).getClass();
    }

    /**
     * 获取对象数据
     * @param clazz 当前对象
     * @return 数组
     */
    protected Field[] getField(Class<?> clazz) {
        Class<?> superClass = this.isSuper(clazz);
        if (superClass != null && superClass.isAnnotationPresent(BatchSuper.class)) {
            return BatchUtils.concat(clazz.getDeclaredFields(), superClass.getDeclaredFields());
        }
        return clazz.getDeclaredFields();
    }

    /**
     * 获取当前对象的父类对象
     *
     * @param clazz 当前对象
     * @return 父类对象
     */
    private Class<?> isSuper(Class<?> clazz) {
        Class<?> superClazz = clazz.getSuperclass();
        return superClazz != null && superClazz != Object.class ? superClazz : null;
    }

    /**
     * 获取表名
     *
     * @param clazz 类
     * @return 表名字符串
     */
    private String getTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(BatchName.class)) {
            throw new RuntimeException("The ORM relational mapping object cannot be resolved");
        }
        return clazz.getAnnotation(BatchName.class).value();
    }

    /**
     * 获取组装好的SQL
     *
     * @param list      数据集合
     * @param fields    类的属性数组
     * @param tableName 表名
     * @return 可执行的SQL
     */
    protected abstract String getSql(List<?> list, Field[] fields, String tableName);

    /**
     * 执行数据库操作
     *
     * @param sql   可执行的SQL语句
     * @param clazz 参数中的对象Class
     * @return 是否成功
     */
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
                    sqlSession.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
