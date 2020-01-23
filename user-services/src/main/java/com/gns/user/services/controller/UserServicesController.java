package com.gns.user.services.controller;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

	@Autowired
	Environment environment;

	Logger logger = LoggerFactory.getLogger(UserServicesController.class);

	@Autowired
	UserRepository userRepository;

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

	// Display All users
	@GetMapping("/users")
	public String getAllUsers(Map<String, Object> model) {
		logger.info("getAllUsers :::::::");
		model.put("usersList", userRepository.findAll());
		return "users_display";
	}
}
