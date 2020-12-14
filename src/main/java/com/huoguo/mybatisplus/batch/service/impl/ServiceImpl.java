package com.huoguo.mybatisplus.batch.service.impl;

import com.huoguo.mybatisplus.batch.service.IService;

import java.util.List;

/**
 * @ClassName ServiceImpl
 * @Description TODO
 * @Author LZH
 * @Date 2020/12/14 14:19
 * @Version 1.0
 */
public class ServiceImpl<T> implements IService<T> {

    @Override
    public boolean saveBatch(List<T> entityList) {
        return false;
    }

    @Override
    public boolean saveBatch(List<T> entityList, int val1) {
        return false;
    }
}
