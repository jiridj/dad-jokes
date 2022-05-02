package com.softwareag.demo.dadjokes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class JokeExistsAdvice {

	@ResponseBody
	@ExceptionHandler(JokeExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public String jokeExistsHandler(JokeExistsException e) {
		return e.getMessage();
	}
	
}