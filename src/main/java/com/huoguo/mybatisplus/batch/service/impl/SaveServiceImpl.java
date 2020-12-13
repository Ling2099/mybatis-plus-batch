package com.huoguo.mybatisplus.batch.service.impl;

import com.huoguo.mybatisplus.batch.service.IService;

import java.util.Collection;

/**
 * @ClassName: SaveServiceImpl
 * @Description: TODO
 * @Author: LZH
 * @Date: 2020/12/13 19:24
 * @Version: 1.0
 */
public class SaveServiceImpl<T> implements IService<T> {

    @Override
    public boolean executeBatch(Collection<T> entityList) {
        return false;
    }

    @Override
    public boolean executeBatch(Collection<T> entityList, int val1) {
        return false;
    }
}
