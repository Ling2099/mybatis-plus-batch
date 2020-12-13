package com.huoguo.mybatisplus.batch.template;

import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;

import java.util.ArrayList;
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

    public final <T> void dohandle(List<T> entityList) {
        isEmpty(entityList);
        TableInfo tableInfo = getTableInfo(getClazz(entityList));
        tableInfo.getTableName();
        String column = getColumn(tableInfo);

    }

    /**
     * @Author: LZH
     * @Description: 判断集合不能为空
     * @Date: 2020/12/13 22:47
     * @param entityList
     * @return void
     */
    private <T> void isEmpty(Collection<T> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            throw new NullPointerException("Data cannot be empty");
        }
    }

    /**
     * @Author: LZH
     * @Description: 获取集合内对象的运行时类
     * @Date: 2020/12/13 22:56
     * @param entityList
     * @return java.lang.Class<?>
     */
    private <T> Class<?> getClazz(List<T> entityList) {
        return entityList.get(DefaultConstants.DEFAULT_INDEX_VALUE).getClass();
    }

    /**
     * @Author: LZH
     * @Description: 获取 TableInfo 实例
     * @Date: 2020/12/13 23:01
     * @param clazz
     * @return com.baomidou.mybatisplus.core.metadata.TableInfo
     */
    private TableInfo getTableInfo(Class<?> clazz) {
        return TableInfoHelper.getTableInfo(clazz);
    }

    /**
     * @Author: LZH
     * @Description: 获取列名拼接后的字符串
     * @Date: 2020/12/13 23:17
     * @param tableInfo
     * @return java.lang.String
     */
    private String getColumn(TableInfo tableInfo) {
        // String sql = SqlMethod.INSERT_LIST.getSql();
        String value = tableInfo.getFieldList().stream().map(item -> item.getEl()).collect(Collectors.joining(","));
        return "(".concat(value).concat(")");
    }
}
