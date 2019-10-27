package com.gns.web.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {

	Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@RequestMapping("/")
	public String comingSoon(Map<String, Object> model) {
		logger.info("coming-soon :::::::");
		return "coming-soon";
	}

	// coming-soon
	@RequestMapping("/home")
	public String welcome(Map<String, Object> model) {
		logger.info("welcome :::::::");
		return "welcome";
	}

}
