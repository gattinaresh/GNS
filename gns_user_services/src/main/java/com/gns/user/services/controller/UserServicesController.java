package com.gns.user.services.controller;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.User;
import com.gns.user.services.service.UserServices;
import com.gns.user.services.util.ExceptionHandlerUtil;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/user")
public class UserServicesController {

	private static final Logger LOGGER = LogManager.getLogger(UserServicesController.class);
	@Autowired
	Environment environment;

	@Autowired
	UserServices userServices;

	@PutMapping(path = "createUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createUser(@RequestBody User userObj) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.validateAndCreateUser(userObj);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@PutMapping(path = "updateUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateUser(@RequestBody User userObj) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.updateUser(userObj);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@DeleteMapping(path = "deleteUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUser(@RequestParam("userId") long userId) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.deleteUserById(userId);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("all")
	public ResponseEntity<String> getAllUser(@RequestParam("start") int start, @RequestParam("end") int end) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(UserServicesConstants.RESULT, userServices.getAllUser(start, end));
			jsonObject.put(UserServicesConstants.STATUS_CODE, 200);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("login")
	public ResponseEntity<String> authenticateUser(@RequestParam String userName, @RequestParam String userPassword) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.authenticateUser(userName, userPassword);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@PostMapping("importUsers")
	public ResponseEntity<String> importUsersByCSV(@RequestParam("import_user_csv") MultipartFile importUsersCsvFile,
			RedirectAttributes redirectAttributes) {
		LocalDateTime startImport = LocalDateTime.now();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.importUsers(importUsersCsvFile);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		LocalDateTime endImport = LocalDateTime.now();
		LOGGER.info("Time for Import In MS:::" + Duration.between(startImport, endImport).toMillis());
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@PostMapping("exportUsers")
	public ResponseEntity<String> exportUsersToCSV(@RequestParam("start") int start, @RequestParam("end") int end) {
		LocalDateTime startImport = LocalDateTime.now();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.exportUsers(start, end);
		} catch (Exception e) {
			jsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			jsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		LocalDateTime endImport = LocalDateTime.now();
		LOGGER.info("Time for Import In MS:::" + Duration.between(startImport, endImport).toMillis());
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	// User Movies Services
	@GetMapping("movies")
	public ResponseEntity<String> getUserMovies(@RequestParam("start") int start, @RequestParam("end") int end) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = userServices.getUserMovies(start, end);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
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
