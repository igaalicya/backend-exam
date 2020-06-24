package com.cimb.movies21.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.movies21.entity.Characters;

public interface CharacterRepo extends JpaRepository<Characters, Integer> {

}
