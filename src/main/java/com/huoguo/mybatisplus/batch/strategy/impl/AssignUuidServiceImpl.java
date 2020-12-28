package com.huoguo.mybatisplus.batch.strategy.impl;

import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @ClassName AssignUuidServiceImpl
 * @Description UUID主键
 * @Author LZH
 * @Date 2020/12/25 11:49
 * @Version 1.0
 */
public class AssignUuidServiceImpl implements StitchingSqlService {

    @Override
    public String getSqlString(List<?> list, Field[] fields, String id) {
        return null;
    }
}
