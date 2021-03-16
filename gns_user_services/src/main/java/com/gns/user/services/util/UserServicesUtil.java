package com.gns.user.services.util;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

import com.gns.user.services.model.User;
import com.gns.user.services.repository.UserRepository;

public class UserServicesUtil {
	private static final Logger LOGGER = LogManager.getLogger(UserServicesUtil.class);

	public void validateAttr(String userAttrVal, String userAttrErrorMsg, JSONArray jsonArray) {
		if (userAttrVal == null || userAttrVal.isEmpty()) {
			jsonArray.put(userAttrErrorMsg);
		}
	}

	public JSONArray validateUserJson(User userObj, UserRepository userRepository) {
		JSONArray jsonArray = new JSONArray();
		try {
			validateAttr(userObj.getName(), "User Name Must not be null or Empty", jsonArray);
			validateAttr(userObj.getPassword(), "User Password Must not be null or Empty", jsonArray);
			validateAttr(userObj.getEmail(), "User Email Address Must not be null or Empty", jsonArray);
			validateAttr(userObj.getCreatedBy(), "User Created By Must not be null or Empty", jsonArray);
			if (jsonArray.length() <= 0) {
				if (userRepository.existsByName(userObj.getName())) {
					jsonArray.put("User Name exists");
				}
				if (userRepository.existsByEmail(userObj.getEmail())) {
					jsonArray.put("Email Id exists ");
				} else if (!EmailValidator.getInstance().isValid(userObj.getEmail())) {
					jsonArray.put("Invalid Email Id");
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return jsonArray;
	}
}
