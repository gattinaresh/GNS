package com.gns.web.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

	Logger logger = LoggerFactory.getLogger(WebController.class);

	@RequestMapping("/")
	public String comingSoonPage(Map<String, Object> model) {
		return "coming-soon";
	}

	@RequestMapping("/home")
	public String welcomePage(Map<String, Object> model) {
		return "welcome";
	}

	@RequestMapping("/login")
	public String loginPage(Map<String, Object> model) {
		return "login";
	}

}
