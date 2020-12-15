package com.huoguo.mybatisplus.batch.test.service.impl;

import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.service.impl.BatchServiceImpl;
import com.huoguo.mybatisplus.batch.test.entity.Test;
import com.huoguo.mybatisplus.batch.test.mapper.TestMapper;
import com.huoguo.mybatisplus.batch.test.service.ITestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LZH
 * @since 2020-12-15
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

    @Override
    public void test() {
        Test test = new Test();
        test.setName("测试1");
        test.setAge(10);

        Test test2 = new Test();
        test2.setName("测试2");
        test2.setAge(11);

        List<Test> list = new ArrayList<>();
        list.add(test);
        list.add(test2);

        BatchService<Test> batchService = new BatchServiceImpl<>();
        batchService.saveBatch(list);
    }
}
