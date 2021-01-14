package com.huoguo.mybatisplus.batch.service;

import java.util.Map;

/**
 * 填充字段接口
 * 需配置实现类，并添加注解@Bean
 *
 * @author Lizhenghuang
 */
public interface BatchFillService {

    /**
     * 获取SQL新增时填充字段与值
     * @return 字段与值的集合
     */
    Map<String, Object> batchInsertFill();

    /**
     * 获取SQL修改时填充字段与值
     * @return 字段与值的集合
     */
    Map<String, Object> batchUpdateFill();
}
