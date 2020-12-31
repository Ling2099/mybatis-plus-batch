package com.huoguo.mybatisplus.batch.service;

import java.util.List;

/**
 * 对外暴露的调用接口
 * @author Lizhenghuang
 */
public interface BatchService {

    /**
     * 批量新增
     * @param list 数据集合
     * @return 是否成功
     */
    Boolean insertBatch(List<?> list);

    /**
     * 批量新增
     * @param list 数据集合
     * @param size 每次写操作的数据集合大小
     * @return 是否成功
     */
    Boolean insertBatch(List<?> list, int size);

    /**
     * 批量删除
     * @param list 数据集合
     * @return 是否成功
     */
    @Deprecated
    Boolean deleteBatch(List<?> list);

    /**
     * 批量删除
     * @param list 数据集合
     * @param size 每次写操作的数据集合大小
     * @return 是否成功
     */
    @Deprecated
    Boolean deleteBatch(List<?> list, int size);
}
