package com.gns.user.services.util;

import org.json.JSONObject;

import com.gns.user.services.constants.UserServicesConstants;

public class ExceptionHandlerUtil {

	private static ExceptionHandlerUtil exceptionHandlerUtil;

	public static ExceptionHandlerUtil getInstance() {
		if (exceptionHandlerUtil == null) {
			exceptionHandlerUtil = new ExceptionHandlerUtil();
		}
		return exceptionHandlerUtil;
	}
	
	public JSONObject getExceptionJSON(Throwable e) {
		JSONObject exceptionJsonObject = new JSONObject();
		exceptionJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
		exceptionJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		return exceptionJsonObject;
	}
}
