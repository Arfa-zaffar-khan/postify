package com.postify.main;

import com.postify.main.services.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostifyApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostifyApplication.class, args);
	}
}
