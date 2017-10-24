package com.czc.bi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.czc.bi.mapper")
@ImportResource("classpath:applicationContext.xml")
@EnableScheduling
public class ZhikeKoubeiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhikeKoubeiApplication.class, args);
	}
}
