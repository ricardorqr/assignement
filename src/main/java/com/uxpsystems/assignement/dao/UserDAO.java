package com.uxpsystems.assignement.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.uxpsystems.assignement.model.User;

public interface UserDAO extends CrudRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);

}
