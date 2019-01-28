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
		Optional<User> savedUser = userService.getUserById(user.getId());

		if (savedUser.isPresent() && savedUser.get().equals(user)) {
			throw new UserException("User has saved already : ID = " + savedUser.get().getId());
		}

		savedUser = userService.getUserByUsername(user.getUsername());

		if (savedUser.isPresent() && savedUser.get().getPassword().equals(user.getPassword())) {
			throw new UserException("User has savedalready: Username = " + savedUser.get().getUsername());
		}

		return userService.saveUser(user);
	}

	@PutMapping("/users/{id}")
	public User updateUSer(@RequestBody User user, @PathVariable long id) {
		Optional<User> savedUser = userService.getUserById(id);

		if (!savedUser.isPresent()) {
			throw new UserException("User already has saved: ID = " + savedUser.get().getId());
		}

		savedUser = userService.getUserByUsername(user.getUsername());

		if (!savedUser.isPresent()) {
			throw new UserException("User already has saved: USername = " + savedUser.get().getUsername());
		}

		return userService.saveUser(user);
	}

}
