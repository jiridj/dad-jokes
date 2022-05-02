package com.softwareag.demo.dadjokes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class JokeNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(JokeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String jokeNotFoundHandler(JokeNotFoundException e) {
		return e.getMessage();
	}
	
}