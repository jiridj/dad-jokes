package com.softwareag.demo.dadjokes.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.softwareag.demo.dadjokes.model.Joke;
import com.softwareag.demo.dadjokes.repository.JokeRepository;

@RestController
public class JokeRestController {

	@Autowired
	private final JokeRepository repo;
	
	public JokeRestController(JokeRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping("/jokes")	
	public Iterable<Joke> all() {
		return repo.findAll();
	}
	
	@GetMapping("/jokes/{id}")
	public Joke one(@PathVariable String id) {
		return repo.findById(id).orElseThrow(() -> new JokeNotFoundException(id)); 
	}
	
	@PostMapping("/jokes")
	public Joke newJoke(@RequestBody Joke newJoke) {
		try { 
			return repo.save(newJoke);
		}
		catch(ConstraintViolationException e) {
			throw new JokeExistsException();
		}
	}
	
	@PutMapping("/jokes/{id}")
	public Joke updateJoke(@RequestBody Joke newJoke, @PathVariable String id) {
		return repo.findById(id)
				.map(joke -> {
					joke.setContent(newJoke.getContent());
					joke.setAccepted(newJoke.isAccepted());
					return repo.save(joke);
				})
				.orElseThrow(() -> new JokeNotFoundException(id));
	}
	
	@DeleteMapping("/jokes/{id}")
	public void deleteJoke(@PathVariable String id) {
		try {
			repo.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new JokeNotFoundException(id);
		}
	}
	
}
