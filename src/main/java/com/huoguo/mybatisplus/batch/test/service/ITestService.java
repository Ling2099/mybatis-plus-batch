package com.huoguo.mybatisplus.batch.test.service;

import com.huoguo.mybatisplus.batch.test.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LZH
 * @since 2020-12-15
 */
public interface ITestService extends IService<Test> {

    void test();
}
