package com.huoguo.mybatisplus.batch.demo;

import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.service.impl.BatchServiceImpl;
import com.huoguo.mybatisplus.batch.template.child.BatchInsertTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Test
 * @Description: TODO
 * @Author: LZH
 * @Date: 2021/1/17 18:39
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);

        BatchService batchService = new BatchServiceImpl();
        batchService.deleteBatch(list, User.class);
    }
}
