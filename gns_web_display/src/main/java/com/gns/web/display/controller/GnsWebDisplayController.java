/**
 * 
 */
package com.gns.web.display.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author naresh
 *
 */
@Controller
public class GnsWebDisplayController {
	Logger logger = LoggerFactory.getLogger(GnsWebDisplayController.class);

	@RequestMapping("/")
	public String homePageDisplay(Map<String, Object> model) {
		return "HomePage";
	}

	@RequestMapping("/signUp")
	public String signUpPageDisplay(Map<String, Object> model) {
		return "SignUpPage";
	}

	@RequestMapping("/login")
	public String loginPageDisplay(Map<String, Object> model) {
		return "LoginPage";
	}
}
