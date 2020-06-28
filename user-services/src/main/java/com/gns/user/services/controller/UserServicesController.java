package com.gns.user.services.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.User;
import com.gns.user.services.service.UserServices;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UserServicesController {

	private static final Logger logger = LoggerFactory.getLogger(UserServicesController.class);

	@Autowired
	Environment environment;

	@Autowired
	UserServices userServices;

	@PostMapping(path = "createUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody User userObj) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.validateAndCreateUser(userObj);
		} catch (Exception e) {
			jsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			jsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("all")
	public ResponseEntity<String> getAllUser() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(UserServicesConstants.RESULT, userServices.getAllUser());
			jsonObject.put(UserServicesConstants.STATUS_CODE, 200);
		} catch (Exception e) {
			jsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			jsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("login")
	public ResponseEntity<String> authenticateUser(@RequestParam String userName, @RequestParam String userPassword) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.authenticateUser(userName, userPassword);
		} catch (Exception e) {
			jsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			jsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("healthStatus")
	public String healthCheck() {
		return "User Services API is running on " + environment.getProperty("local.server.port") + " Port";
	}
}
