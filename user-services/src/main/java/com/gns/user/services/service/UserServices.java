package com.gns.user.services.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.User;
import com.gns.user.services.repository.UserRepository;
import com.gns.user.services.security.TokenService;
import com.gns.user.services.util.UserServicesUtil;

@Service
public class UserServices {

	private static final Logger LOGGER = LogManager.getLogger(UserServices.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenService tokenService;

	public JSONObject validateAndCreateUser(User user) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			UserServicesUtil userServicesUtil = new UserServicesUtil();
			JSONArray userObjErrors = userServicesUtil.validateUserJson(user, userRepository);
			if (userObjErrors.length() > 0) {
				LOGGER.info("User Has errors:::");
				responseJsonObject.put(UserServicesConstants.STATUS_CODE, 400);
				responseJsonObject.put(UserServicesConstants.RESULT, userObjErrors);
			} else {
				LOGGER.info("Saving User In Repo:::");
				LocalDateTime now = LocalDateTime.now();
				user.setCreatedDateTime(now);
				user.setModifiedDateTime(now);
				LOGGER.info("User Saving::" + user.toString());
				userRepository.save(user);
				responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			}
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			responseJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return responseJsonObject;
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	public JSONObject authenticateUser(String userName, String userPassword) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			boolean isUserAuthenticated = userRepository.autheticateUser(userName, userPassword);
			if (isUserAuthenticated) {
				responseJsonObject.put(UserServicesConstants.JWT_TOKEN, tokenService.addAuthentication(userName));
			} else {
				responseJsonObject.put(UserServicesConstants.IS_USER_AUTHENTICATED, isUserAuthenticated);
			}
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			responseJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return responseJsonObject;
	}


	public JSONObject importUsers(MultipartFile importUsersCsvFile) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			LOGGER.info("file.getContentType():::" + importUsersCsvFile.getContentType());
			if (!importUsersCsvFile.isEmpty()) {
				responseJsonObject.put(UserServicesConstants.RESULT,
						processCSVFile(importUsersCsvFile.getInputStream()));
				responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			} else {
				responseJsonObject.put(UserServicesConstants.STATUS_CODE, 400);
				responseJsonObject.put(UserServicesConstants.MESSAGE, "InValid Data");
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			responseJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return responseJsonObject;
	}

	private JSONArray processCSVFile(InputStream is) {
		JSONArray jsonArray = new JSONArray();
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				User user = new User();
				user.setCreatedBy(csvRecord.get("createdBy"));
				user.setName(csvRecord.get("userName"));
				user.setPassword(csvRecord.get("password"));
				user.setEmail(csvRecord.get("userEmail"));
				jsonArray.put(validateAndCreateUser(user));
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		return jsonArray;
	}
}
