package com.cimb.movies21.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cimb.movies21.entity.Movies;

public interface MoviesRepo extends JpaRepository<Movies, Integer> {
//	String query1 = "select c.nama, count(c.nama) from movcat mc join movies m on mc.movies_id = m.id "
//			+ "join categories c on mc.categories_id = c.id group by c.nama";
	
	@Query(value = "SELECT * FROM Movies WHERE tahun = :year", nativeQuery = true)
	public Iterable<Movies> findMoviesInYear(@Param("year") Integer year);
	
	
//	@Query(value =query1, nativeQuery = true)
//	public Iterable<Movies> countMoviesinCat();
	

}
