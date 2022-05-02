package com.softwareag.demo.dadjokes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Dad jokes API",
		version = "1.0",
		description = "An API for maintaining the ultimate collection of curated dad jokes."
))
public class DadJokeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DadJokeApplication.class, args);
	}
	
}
