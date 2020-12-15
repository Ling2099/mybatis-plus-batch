package com.huoguo.mybatisplus.batch.template;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: AbstractMethod
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/13 21:31
 * @Version: 1.0
 */
public abstract class AbstractMethod {

    public final <T> void dohandle(List<T> entityList, int size) {
        isEmpty(entityList);
        // 获取泛型实例
        Class<?> clazz = getClazz(entityList);
        // 获取表映射信息
        TableInfo tableInfo = getTableInfo(clazz);

        // 需要获取表名
        // tableInfo.getTableName();
        // 需要获取字段名称
        // tableInfo.getFieldList();

        // 拆分list
        calcSize(tableInfo, entityList, size);
        // sql的前半部分
        // spliceSql(tableInfo, entityList);
        // 值
        // ???

        // 执行事务 提交
        // ???
    }

    /**
     * @Author: LZH
     * @Description: 判断集合不能为空或集合长度不等于0
     * @Date: 2020/12/13 22:47
     * @param entityList 来自客户端的数据集合
     * @return void
     */
    private <T> void isEmpty(List<T> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            throw new NullPointerException("Data cannot be empty");
        }
    }

    /**
     * @Author: LZH
     * @Description: 获取集合内对象的运行时类，以方便获取对应的表信息对象
     * @Date: 2020/12/13 22:56
     * @param entityList 来自客户端的数据集合
     * @return java.lang.Class<?>
     */
    private <T> Class<?> getClazz(List<T> entityList) {
        return entityList.get(DefaultConstants.DEFAULT_INDEX_VALUE).getClass();
    }

    /**
     * @Author: LZH
     * @Description: 获取 TableInfo 实例对象
     * @Date: 2020/12/13 23:01
     * @param clazz 当前运行时类
     * @return com.baomidou.mybatisplus.core.metadata.TableInfo
     */
    private TableInfo getTableInfo(Class<?> clazz) {
        return TableInfoHelper.getTableInfo(clazz);
    }

    // 我觉得这里应该返回字符串SQL
    private <T> void calcSize(TableInfo tableInfo, List<T> entityList, int size) {
        int total = entityList.size();
        if (total <= size) {
            // 直接执行
            spliceSql(tableInfo, entityList);
        } else {
            //
            int limit = total / size;
            int residue = total % size;

            for (int i = 0 ; i < limit; i ++) {
                // 分批执行
                // doInsert(clazzPath, list.subList(i * 100, 100 * (i + 1)));
            }

            if (residue > 0) {
                // 再次执行剩余的
            }
        }
    }

    /**
     * @Author LZH
     * @Description 这里应该是拼接SQL
     * @Date 2020/12/14 9:39
     * @Param tableInfo MybatisPlus中的表信息对象
     * @Param entityList 来自客户端的数据集合
     * @return void
     **/
    protected abstract <T> void spliceSql(TableInfo tableInfo, List<T> entityList);

    // 这个方法没用了
//    private String getColumn(TableInfo tableInfo) {
//        // String sql = SqlMethod.INSERT_LIST.getSql();
//        String value = tableInfo.getFieldList().stream().map(item -> item.getEl()).collect(Collectors.joining(","));
//        return DefaultConstants.LEFT_PARENTHESIS.concat(value).concat(DefaultConstants.RIGHT_PARENTHESIS);
//    }

    // 这里提交事务 注意事务回滚

    protected void command(String sql) {
        System.out.println(sql);
    }
}
