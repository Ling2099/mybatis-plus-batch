package com.huoguo.mybatisplus.batch.service.impl;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.template.child.InsertChildTemplate;

import java.util.List;

/**
 * @ClassName BatchServiceImpl
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/25 17:20
 * @Version 1.0
 */
public class BatchServiceImpl implements BatchService {

    @Override
    public Boolean insertBatch(List<?> list) {
        AbstractTemplate insert = new InsertChildTemplate();
        return insert.bacth(list, DefaultConstants.DEFAULT_BATCH_SIZE);
    }

    @Override
    public Boolean insertBatch(List<?> list, int size) {
        AbstractTemplate insert = new InsertChildTemplate();
        return insert.bacth(list, size);
    }
}
