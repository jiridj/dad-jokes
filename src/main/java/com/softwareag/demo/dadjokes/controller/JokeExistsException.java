package com.softwareag.demo.dadjokes.controller;

@SuppressWarnings("serial")
public class JokeExistsException extends RuntimeException {
	
	public JokeExistsException() {
		super("This joke already exists in our database.");
	}

}
