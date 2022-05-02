package com.softwareag.demo.dadjokes.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.dao.EmptyResultDataAccessException;

import com.softwareag.demo.dadjokes.model.Joke;
import com.softwareag.demo.dadjokes.repository.JokeRepository;

@ExtendWith(MockitoExtension.class)
public class JokeRestControllerTest {

	@InjectMocks
	private JokeRestController controller;
	
	@Mock
	private JokeRepository repo;
	
	private static Iterable<Joke> initializeJokes() {
		List<Joke> jokes = new ArrayList<Joke>();
		
		jokes.add(Joke.builder()
				.id(UUID.randomUUID().toString())
				.content("This is the first dad joke")
				.accepted(true)
				.build()
				);
		
		jokes.add(Joke.builder()
				.id(UUID.randomUUID().toString())
				.content("This is the second dad joke")
				.accepted(false)
				.build()
				);
		
		return jokes;
	}
	
	@Test
	public void testAll() {	
		when(repo.findAll()).thenReturn(initializeJokes());
		
		Iterable<Joke> jokes = controller.all();
		assertThat(((Collection<Joke>) jokes).size()).isEqualTo(2);
	}
	
	@Test 
	public void testOneFound() {
		Iterable<Joke> jokes = initializeJokes();
		Joke firstJoke = jokes.iterator().next();
		when(repo.findById(firstJoke.getId())).thenReturn(Optional.of(firstJoke));
		
		Joke joke = controller.one(firstJoke.getId());
		assertThat(joke).isEqualTo(firstJoke);
	}
	
	@Test
	public void testOneNotFound() {
		assertThatThrownBy(() -> { controller.one("doesnotexist"); })
			.isInstanceOf(JokeNotFoundException.class);
	}
	
	@Test 
	public void testNewJoke() {
		Joke newJoke = Joke.builder()
				.content("This is the first dad joke")
				.accepted(true)
				.build();
		
		when(repo.save(newJoke)).thenAnswer(new Answer<Joke>() {
			public Joke answer(InvocationOnMock invocation) {
				Joke joke = (Joke) invocation.getArgument(0);
				joke.setId(UUID.randomUUID().toString());
				return joke;
			}
		});
		
		Joke joke = controller.newJoke(newJoke);
		assertThat(joke.getId()).isNotBlank();
	}
	
	@Test
	public void testNewJokeExists() {
		Joke newJoke = Joke.builder()
				.content("This joke already exists")
				.accepted(true)
				.build();
		
		when(repo.save(newJoke)).thenThrow(ConstraintViolationException.class);
		
		assertThatThrownBy(() -> { controller.newJoke(newJoke); })
			.isInstanceOf(JokeExistsException.class);
	}
	
	@Test
	public void testUpdateJoke() {
		Iterable<Joke> jokes = initializeJokes();
		Joke firstJoke = jokes.iterator().next();
		firstJoke.setAccepted(false);
		
		when(repo.findById(firstJoke.getId())).thenReturn(Optional.of(firstJoke));
		when(repo.save(firstJoke)).thenReturn(firstJoke);
		
		Joke joke = controller.updateJoke(firstJoke, firstJoke.getId());
		assertThat(joke.getId()).isNotBlank();
		assertThat(!joke.isAccepted());
	}
	
	@Test
	public void testUpdateJokeNotFound() {
		assertThatThrownBy(() -> { controller.updateJoke(
					Joke.builder()	
						.id("doesnotexist")
						.content("This dad joke does not exist")
						.accepted(false)
						.build(),
					"doesnotexist"
				); 
			}).isInstanceOf(JokeNotFoundException.class);
	}
	
	@Test
	public void testDeleteJoke() {
		doNothing().when(repo).deleteById("someid");
		controller.deleteJoke("someid");
	}
	
	@Test
	public void testDeleteJokeNotFound() {
		doThrow(EmptyResultDataAccessException.class).when(repo).deleteById("doesnotexist");
		
		assertThatThrownBy(() -> { controller.deleteJoke("doesnotexist"); })
			.isInstanceOf(JokeNotFoundException.class);
	}
	
}
