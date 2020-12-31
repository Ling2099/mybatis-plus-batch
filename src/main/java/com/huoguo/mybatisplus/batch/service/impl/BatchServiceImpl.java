package com.huoguo.mybatisplus.batch.service.impl;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.template.child.InsertChildTemplate;

import java.util.List;

/**
 * 对外暴露的调用接口实现类
 * @author Lizhenghuang
 */
public class BatchServiceImpl implements BatchService {

    /**
     * 批量新增
     * @param list 数据集合
     * @return 是否成功
     */
    @Override
    public Boolean insertBatch(List<?> list) {
        AbstractTemplate insert = new InsertChildTemplate();
        return insert.bacth(list, DefaultConstants.DEFAULT_BATCH_SIZE);
    }

    /**
     * 批量新增
     * @param list 数据集合
     * @param size 每次写操作的数据集合大小
     * @return 是否成功
     */
    @Override
    public Boolean insertBatch(List<?> list, int size) {
        AbstractTemplate insert = new InsertChildTemplate();
        return insert.bacth(list, size);
    }

    /**
     * 批量删除
     * @param list 数据集合
     * @return 是否成功
     */
    @Deprecated
    @Override
    public Boolean deleteBatch(List<?> list) {
        return null;
    }

    /**
     * 批量删除
     * @param list 数据集合
     * @param size 每次写操作的数据集合大小
     * @return 是否成功
     */
    @Deprecated
    @Override
    public Boolean deleteBatch(List<?> list, int size) {
        return null;
    }
}
