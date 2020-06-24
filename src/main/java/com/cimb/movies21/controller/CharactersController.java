package com.cimb.movies21.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.movies21.dao.CharacterRepo;
import com.cimb.movies21.dao.MoviesRepo;
import com.cimb.movies21.entity.Characters;
import com.cimb.movies21.entity.Movies;

@RestController
@RequestMapping("/characters")
public class CharactersController {
	@Autowired
	private MoviesRepo moviesRepo;
	
	@Autowired 
	private CharacterRepo characterRepo;
	
	@GetMapping
	public Iterable<Characters> getAllCharacters() {
		return characterRepo.findAll();
	}
	
	@PostMapping("/movies/{moviesId}")
	public Characters addCharacters(@RequestBody Characters characters, @PathVariable int moviesId) {
		Movies findMovies = moviesRepo.findById(moviesId).get();

		if (findMovies == null)
			throw new RuntimeException("Movies not found");

		characters.setMovies(findMovies);
		
		return characterRepo.save(characters);
	}
	
	@PutMapping("/editCharacters")
	public Characters editCharacters(@RequestBody Characters characters) {
		Optional<Characters> findCharacters = characterRepo.findById(characters.getId());

		if (findCharacters.toString() == "Optional.empty")
			throw new RuntimeException("Movies with id " + characters.getId() + " does not exist");

		return characterRepo.save(characters);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteCharactersById(@PathVariable int id ) {
		Optional<Characters> findCharacters = characterRepo.findById(id);
		
		if (findCharacters.toString()=="Optional.empty")
			throw new RuntimeException("Movies Not Found");
			
		characterRepo.deleteById(id);
	}
}
