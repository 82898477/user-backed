package com.example.userbacked;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author 82898
 */
@SpringBootApplication
@MapperScan("com.example.userbacked.mapper")
public class UserBackedApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserBackedApplication.class, args);
	}

}
