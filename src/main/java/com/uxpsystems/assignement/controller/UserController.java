package com.uxpsystems.assignement.controller;

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

import com.uxpsystems.assignement.exception.UserException;
import com.uxpsystems.assignement.model.User;
import com.uxpsystems.assignement.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/users/{id}")
	public User getUser(@PathVariable long id) {
		Optional<User> user = userService.getUserById(id);

		if (!user.isPresent()) {
			throw new UserException("Unable to find user: ID = " + id);
		}

		return user.get();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable long id) {
		User user = new User();
		user.setId(id);
		userService.deleteUser(user);
	}

	@PostMapping("/users")
	public User saveUser(@RequestBody User user) {
		try {
			return userService.saveUser(user);
		} catch (Exception e) {
			throw new UserException(e.getMessage());
		}
	}

	@PutMapping("/users/{id}")
	public User updateUSer(@RequestBody User user, @PathVariable long id) {
		Optional<User> savedUSer = userService.getUserById(id);

		if (!savedUSer.isPresent()) {
			throw new UserException("Unable to find user: ID = " + id);
		}

		return userService.saveUser(user);
	}

}
