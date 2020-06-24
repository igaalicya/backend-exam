package com.cimb.movies21.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.movies21.entity.Categories;

public interface CategoriesRepo extends JpaRepository<Categories, Integer> {

}
