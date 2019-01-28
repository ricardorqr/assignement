package com.uxpsystems.assignement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uxpsystems.assignement.dao.UserDAO;
import com.uxpsystems.assignement.exception.UserException;
import com.uxpsystems.assignement.model.User;

@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;

	public List<User> getAllUsers() {
		return (List<User>) userDAO.findAll();
	}

	public Optional<User> getUserById(long id) {
		return userDAO.findById(id);
	}

	public void deleteUser(User user) {
		userDAO.delete(user);
	}

	public User saveUser(User user) throws UserException {
		return userDAO.save(user);
	}

	public User updateUser(User user) {
		return userDAO.save(user);
	}

}
