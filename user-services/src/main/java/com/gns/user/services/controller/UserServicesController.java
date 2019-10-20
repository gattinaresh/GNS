package com.gns.user.services.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gns.user.services.model.User;
import com.gns.user.services.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class UserServicesController {

	Logger logger = LoggerFactory.getLogger(UserServicesController.class);

	@Autowired
	UserRepository userRepository;

	// Get All users
	@GetMapping("/users")
	List<User> getAllUsers() {
		return userRepository.findAll();
	}

	// Create a new Note
	@PostMapping("/create")
	public User createNote(@Valid @RequestBody User user) {
		return userRepository.save(user);
	}

	// Delete a Note
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long userId) {
		userRepository.deleteById(null);
		return ResponseEntity.ok().build();
	}
}
