package com.huoguo.mybatisplus.batch.injector;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.huoguo.mybatisplus.batch.enums.SqlMethod;
import com.huoguo.mybatisplus.batch.filler.FillerHandler;
import com.huoguo.mybatisplus.batch.template.AbstractMethod;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;
import java.util.Map;
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

        try {
            String sql = FillerHandler.handleId(tableInfo, entityList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.command("");
    }

}
