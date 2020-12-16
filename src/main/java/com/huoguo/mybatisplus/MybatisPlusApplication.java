package com.huoguo.mybatisplus;

import com.huoguo.mybatisplus.batch.config.MybatisPlusObjectHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@MapperScan("com.huoguo.mybatisplus.batch.test.**.mapper")
@Import({MybatisPlusObjectHandler.class})
@SpringBootApplication
public class MybatisPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisPlusApplication.class, args);
	}

}
