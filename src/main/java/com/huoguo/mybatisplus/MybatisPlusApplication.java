package com.huoguo.mybatisplus;

import com.huoguo.mybatisplus.batch.service.BatchService;
import com.huoguo.mybatisplus.batch.service.impl.BatchServiceImpl;
import com.huoguo.mybatisplus.batch.template.AbstractTemplate;
import com.huoguo.mybatisplus.batch.template.child.InsertChildTemplate;
import com.huoguo.mybatisplus.batch.test.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@MapperScan("com.huoguo.mybatisplus")
@SpringBootApplication
public class MybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusApplication.class, args);

		List<User> list = new ArrayList<>();

		for (int i = 0; i < 100001; i ++) {
			list.add(new User()
					.setId(Integer.toUnsignedLong(i))
					.setName("张三" + i)
					.setAge(i)
					.setMsg("用户" + i)
					.setSex(i));
		}
		System.out.println("集合大小为：" + list.size());

		long startTime = System.currentTimeMillis();
		// ======================================

		BatchService batchService = new BatchServiceImpl();
		batchService.insertBatch(list, 10000);

		// ======================================
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
	}
}
