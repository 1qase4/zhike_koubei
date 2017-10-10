package com.czc.bi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("applicationContext.xml")
public class ZhikeKoubeiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhikeKoubeiApplication.class, args);
	}
}
