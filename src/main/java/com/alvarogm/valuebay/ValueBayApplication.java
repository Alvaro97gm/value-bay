package com.alvarogm.valuebay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ValueBayApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(ValueBayApplication.class, args);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// CORS Allowed origin - Vue frontend server
		registry
			.addMapping("/**")
			.allowedOrigins("http://localhost:8080");
	}
}
