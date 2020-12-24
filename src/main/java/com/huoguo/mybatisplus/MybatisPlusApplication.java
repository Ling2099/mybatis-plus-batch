package com.huoguo.mybatisplus;

import com.huoguo.mybatisplus.batch.entity.Test;
import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.service.impl.BatchServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@MapperScan("com.huoguo.mybatisplus.batch.**.mapper")
@SpringBootApplication
public class MybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusApplication.class, args);
		Test test = new Test();
		test.setName("测试1");
		test.setAge(10);
		test.setCreator("张三");
		test.setDeptId(10L);
		test.setCreatorId(1L);

		Test test2 = new Test();
		test2.setName("测试2");
		test2.setAge(11);
		test.setCreator("张三");
		test.setDeptId(10L);
		test.setCreatorId(1L);

		List<Test> list = new ArrayList<>();
		list.add(test);
		list.add(test2);
		BatchService batchService = new BatchServiceImpl();
		batchService.insert(list);
	}

}
