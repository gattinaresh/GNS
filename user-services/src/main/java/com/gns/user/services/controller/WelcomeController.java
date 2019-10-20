package com.gns.user.services.controller;

import java.net.InetAddress;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * Home Page Controller
 */
@Controller
public class WelcomeController {

	Logger logger = LoggerFactory.getLogger(WelcomeController.class);

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {

		try {
			String host = InetAddress.getLocalHost().getHostAddress();
			model.put("host", host);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "welcome";
	}
}
