package com.janiya.reviews_app_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.janiya.reviews_app_springboot")
public class ReviewsAppSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsAppSpringbootApplication.class, args);
	}

}