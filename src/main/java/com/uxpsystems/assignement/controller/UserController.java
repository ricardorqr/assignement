package com.uxpsystems.assignement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@RequestMapping("/assignement") //http://localhost:9090/assignement
@CacheConfig(cacheNames = { "users" })
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@Cacheable(key = "#id")
	@GetMapping("/users/{id}")
	public User getUser(@PathVariable Long id) {
		Optional<User> user = userService.getUserById(id);

		if (!user.isPresent()) {
			throw new UserException("Unable to find user: ID = " + id);
		}

		return user.get();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable Long id) {
		User user = new User();
		user.setId(id);
		userService.deleteUser(user);
	}

	@PostMapping("/users")
	public User saveUser(@RequestBody User user) {
		if (user.getId() != null) {
			if (checkUserByID(user)) {
				throw new UserException("Already inserted value: ID = " + user.getId());
			}

			if (checkUserByUsername(user)) {
				throw new UserException("Already inserted value: Username = " + user.getUsername());
			}
		} else {
			if (checkUserByUsername(user)) {
				throw new UserException("Already inserted value: Username = " + user.getUsername());
			}
		}

		return userService.saveUser(user);
	}

	@PutMapping("/users/{id}")
	public User updateUser(@RequestBody User user, @PathVariable Long id) {
		if (checkUserByUsername(user)) {
			throw new UserException("Already inserted value: Username = " + user.getUsername());
		}

		user.setId(id);
		return userService.updateUser(user);
	}

	private boolean checkUserByUsername(User user) {
		Optional<User> savedUser = userService.getUserByUsername(user.getUsername());
		return savedUser.isPresent() && savedUser.get().getUsername().equals(user.getUsername());
	}

	private boolean checkUserByID(User user) {
		Optional<User> savedUser = userService.getUserById(user.getId());
		return savedUser.isPresent() && savedUser.get().equals(user);
	}

}
