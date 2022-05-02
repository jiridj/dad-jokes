package com.softwareag.demo.dadjokes.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softwareag.demo.dadjokes.model.Joke;

@Repository
public interface JokeRepository extends CrudRepository<Joke, String>{

}
