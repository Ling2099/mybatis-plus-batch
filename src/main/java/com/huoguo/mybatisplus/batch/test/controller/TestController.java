package com.huoguo.mybatisplus.batch.test.controller;


import com.huoguo.mybatisplus.batch.test.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LZH
 * @since 2020-12-15
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ITestService iTestService;

    @GetMapping("getAdd")
    public void getAdd() {
        iTestService.test();
    }
}
