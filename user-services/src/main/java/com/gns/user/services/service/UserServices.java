package com.gns.user.services.service;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.User;
import com.gns.user.services.repository.UserRepository;
import com.gns.user.services.security.TokenService;
import com.gns.user.services.util.UserServicesUtil;

@Service
public class UserServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServices.class);
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
				userRepository.save(user);
				responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
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
			e.printStackTrace();
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			responseJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return responseJsonObject;
	}

}
