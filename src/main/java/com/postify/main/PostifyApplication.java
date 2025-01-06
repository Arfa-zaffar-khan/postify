package com.postify.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PostifyApplication {

	public static void main(String[] args) {
		System.out.println("hello world");
		SpringApplication.run(PostifyApplication.class, args);
	}

}
