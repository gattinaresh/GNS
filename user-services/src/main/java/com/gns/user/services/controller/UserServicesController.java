package com.gns.user.services.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.User;
import com.gns.user.services.service.UserServices;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UserServicesController {

	private static final Logger LOGGER = LogManager.getLogger(UserServicesController.class);
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

	@PostMapping("importUsers")
	public ResponseEntity<String> handleFileUpload(@RequestParam("import_user_csv") MultipartFile importUsersCsvFile,
			RedirectAttributes redirectAttributes) {
		JSONObject jsonObject = new JSONObject();
		try {
			LOGGER.info("In handleFileUpload");
			jsonObject = userServices.importUsers(importUsersCsvFile);
		} catch (Exception e) {
			jsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			jsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("healthStatus")
	public String healthCheck() {
		LOGGER.debug("Debug Message Logged !!!");
		LOGGER.info("Info Message Logged !!!");
		return "User Services API is running on " + environment.getProperty("local.server.port") + " Port";
	}
}
