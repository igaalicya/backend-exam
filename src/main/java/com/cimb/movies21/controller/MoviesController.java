package com.cimb.movies21.controller;

import java.util.List;
//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.movies21.dao.CategoriesRepo;
import com.cimb.movies21.dao.CharacterRepo;
import com.cimb.movies21.dao.MoviesRepo;
import com.cimb.movies21.entity.Categories;
import com.cimb.movies21.entity.Movies;

@RestController
@RequestMapping("/movies")
public class MoviesController {
	
	@Autowired
	private MoviesRepo moviesRepo;
	
	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Autowired
	private CharacterRepo characterRepo;
	
	@GetMapping
	public Iterable<Movies> getMovies(){
		return moviesRepo.findAll();
	}
	
	@GetMapping("/{moviesId}")
	public Movies getMoviesById(@PathVariable int moviesId) {	
		 return moviesRepo.findById(moviesId).get();	
	}
	
	@PostMapping
	public Movies addMovies(@RequestBody Movies movies) {
		return moviesRepo.save(movies);
	}
	
//	@PutMapping("/editMovies")
//	public Movies editMovies(@RequestBody Movies movies) {
//		Optional<Movies> findMovies = moviesRepo.findById(movies.getId());
//
//		if (findMovies.toString() == "Optional.empty")
//			throw new RuntimeException("Movies with id " + movies.getId() + " does not exist");
//
//		return moviesRepo.save(movies);
//	}
	
	// ini versi gak ilang
	@PutMapping("/editMovies")
	public Movies editMovies(@RequestBody Movies movies) {
		Movies findMovies = moviesRepo.findById(movies.getId()).get();

		movies.setCategories(findMovies.getCategories());
		return moviesRepo.save(movies);
	}

	
	@DeleteMapping("/{moviesId}")
	public void deleteMovie(@PathVariable int moviesId) {
		Movies findMovies = moviesRepo.findById(moviesId).get();
		
		findMovies.getCharacters().forEach(characters -> {
			characters.setMovies(null);
			characterRepo.save(characters);
		});
		
		findMovies.getCategories().forEach(categories -> {
			List<Movies> movieCategory = categories.getMovies();
			movieCategory.remove(findMovies);
			categoriesRepo.save(categories);
		});
		
		findMovies.setCategories(null);
		findMovies.setCharacters(null);

		moviesRepo.deleteById(moviesId);
		moviesRepo.save(findMovies);
	}
	

	//	add Categories to movies
	@PostMapping("/{moviesId}/categories/{categoriesId}")
	public Movies addCategoriesToMovies(@PathVariable int moviesId, @PathVariable int categoriesId) {
		Movies findMovies = moviesRepo.findById(moviesId).get();
		
		Categories findCategories = categoriesRepo.findById(categoriesId).get();

		findMovies.getCategories().add(findCategories);
		
		return moviesRepo.save(findMovies);
	}
	
	// delete categories from movies
	@DeleteMapping("/{moviesId}/categories/{categoriesId}")
	public Movies removeMoviesCategory (@PathVariable int moviesId, @PathVariable int categoriesId) {
		Movies findMovies = moviesRepo.findById(moviesId).get();
		
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		
		findMovies.getCategories().remove(findCategories);
		return moviesRepo.save(findMovies);
	}
	
	@GetMapping("/custom")
	public Iterable<Movies> customQueryGet(@RequestParam Integer year){
		return moviesRepo.findMoviesInYear(year);
	}

}
