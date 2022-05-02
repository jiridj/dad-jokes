package com.softwareag.demo.dadjokes.controller;

@SuppressWarnings("serial")
public class JokeNotFoundException extends RuntimeException {
	
	public JokeNotFoundException(String id) {
		super("Could not find joke with id " + id);
	}
	
}
