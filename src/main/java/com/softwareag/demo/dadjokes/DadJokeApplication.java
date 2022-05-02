package com.softwareag.demo.dadjokes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "${application.name}",
		version = "${application.version}",
		description = "${application.description}"
))
public class DadJokeApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DadJokeApplication.class, args);
	}
	
}
