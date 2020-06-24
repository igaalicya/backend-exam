package com.cimb.movies21.controller;

import java.util.List;
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

import com.cimb.movies21.dao.CategoriesRepo;
import com.cimb.movies21.dao.MoviesRepo;
import com.cimb.movies21.entity.Categories;
import com.cimb.movies21.entity.Movies;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

	@Autowired
	private CategoriesRepo categoriesRepo;
	
	@Autowired
	private MoviesRepo moviesRepo;
	
	@GetMapping
	public Iterable<Categories> getAllCategories() {
		return categoriesRepo.findAll();
	}
	
	@GetMapping("/{categoriesId}")
	public Categories getCategoriesById(@PathVariable int categoriesId) {	
		 return categoriesRepo.findById(categoriesId).get();	
	}
	
	// Get Movies in Category
	@GetMapping("/{categoriesId}/movies")
	public List<Movies> getMoviesOfCategories (@PathVariable int categoriesId){
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		
		return findCategories.getMovies();
	}
	
	@PostMapping
	public Categories addCategories(@RequestBody Categories categories) {
		return categoriesRepo.save(categories);
	}
	
//	@PutMapping("/editCategories")
//	public Categories editCategories(@RequestBody Categories categories) {
//		Optional<Categories> findCategories = categoriesRepo.findById(categories.getId());
//
//		if (findCategories.toString() == "Optional.empty")
//			throw new RuntimeException("Movies with id " + categories.getId() + " does not exist");
//
//		return categoriesRepo.save(categories);
//	}
	
	@PutMapping("/editCategories")
	public Categories editCategories(@RequestBody Categories categories) {
		Categories findCategories = categoriesRepo.findById(categories.getId()).get();
		
		categories.setMovies(findCategories.getMovies());

		return categoriesRepo.save(categories);
	}
	
	@DeleteMapping("/{categoriesId}")
	public void deleteCategories(@PathVariable int categoriesId) {
		Categories findCategories = categoriesRepo.findById(categoriesId).get();
		
		findCategories.getMovies().forEach(movies -> {
			List<Categories> moviesCategory = movies.getCategories();
			moviesCategory.remove(findCategories);
			moviesRepo.save(movies);
		});
		
		findCategories.setMovies(null);
		categoriesRepo.save(findCategories);
		categoriesRepo.deleteById(categoriesId);

	}
	
}
