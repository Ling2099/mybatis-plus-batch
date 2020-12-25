package com.huoguo.mybatisplus.batch.strategy.impl;

import com.huoguo.mybatisplus.batch.strategy.StitchingSqlService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName AssignIdServiceImpl
 * @Description 雪花ID主键
 * @Author LZH
 * @Date 2020/12/25 11:49
 * @Version 1.0
 */
public class AssignIdServiceImpl implements StitchingSqlService {

    @Override
    public String getSqlString(String column, List<Map<String, Object>> list) {
        return null;
    }
}
