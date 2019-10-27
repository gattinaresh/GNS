package com.gns.user.services.controller;

import java.net.InetAddress;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gns.user.services.repository.UserRepository;

@Controller
public class WelcomeController {

	Logger logger = LoggerFactory.getLogger(WelcomeController.class);
	@Autowired
	UserRepository userRepository;

	@RequestMapping("/")
	public String comingSoon(Map<String, Object> model) {
		logger.info("coming-soon :::::::");
		try {
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "coming-soon";
	}
	
	//coming-soon
	@RequestMapping("/home")
	public String welcome(Map<String, Object> model) {
		logger.info("welcome :::::::");
		try {
			String host = InetAddress.getLocalHost().getHostAddress();
			model.put("host", host);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "welcome";
	}

	// Display All users
	@GetMapping("/users")
	public String getAllUsers(Map<String, Object> model) {
		logger.info("getAllUsers :::::::");
		model.put("usersList", userRepository.findAll());
		return "users_display";
	}
}
