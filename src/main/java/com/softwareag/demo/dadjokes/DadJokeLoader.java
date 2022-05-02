package com.softwareag.demo.dadjokes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.softwareag.demo.dadjokes.model.Joke;
import com.softwareag.demo.dadjokes.repository.JokeRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class DadJokeLoader {

	@Bean
	CommandLineRunner initJokes(JokeRepository repo) {
		return args -> {
			log.info("Loading dad jokes");
			
			int count = 0;
			InputStream is = getClass().getClassLoader().getResourceAsStream("dad_jokes.txt");
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String joke;
				while((joke = br.readLine()) != null) {
					Joke newJoke = Joke.builder()
							.content(joke)
							.accepted(true)
							.build();
					
					repo.save(newJoke);
					count++;
				}
			}
			catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
			
			log.info("Finished loading {} dad jokes", count);
		};
	}
	
}
