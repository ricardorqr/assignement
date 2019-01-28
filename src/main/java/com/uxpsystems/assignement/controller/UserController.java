package com.uxpsystems.assignement.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.jws.soap.SOAPBinding.Use;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		User user = new User();
		user.setId(id);

		Optional<User> optional = userService.getUserById(user);

//		if (!optional.isPresent())
//			throw new UserException("id-" + id);

		return optional.get();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable long id) {
		User user = new User();
		user.setId(id);
		userService.deleteUser(user);
	}

	@PostMapping("/users")
	public ResponseEntity<Object> saveUser(@RequestBody User user) {
		User savedUser = userService.saveUser(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Object> updateUSer(@RequestBody User user, @PathVariable long id) {
		user.setId(id);
		Optional<User> savedUSer = userService.getUserById(user);

		if (! savedUSer.isPresent())
			return ResponseEntity.notFound().build();
		else {
			userService.saveUser(user);
		}

		return ResponseEntity.noContent().build();
	}

}
