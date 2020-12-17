package com.huoguo.mybatisplus.batch.service.impl;

import com.huoguo.mybatisplus.batch.constant.DefaultConstants;
import com.huoguo.mybatisplus.batch.injector.InsertTemplate;
import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.template.AbstractMethod;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @ClassName BatchServiceImpl
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/14 14:19
 * @Version 1.0
 */
public class BatchServiceImpl<T> implements BatchService<T> {

    @Override
    public boolean saveBatch(List<T> entityList) {
        AbstractMethod insert = new InsertTemplate();
        insert.dohandle(entityList, DefaultConstants.DEFAULT_BATCH_SIZE);
        return false;
    }

    @Override
    public boolean saveBatch(List<T> entityList, int val1) {
        return false;
    }
}
