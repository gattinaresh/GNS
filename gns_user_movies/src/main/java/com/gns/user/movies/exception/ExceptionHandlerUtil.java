package com.gns.user.movies.exception;

import org.json.JSONObject;

import com.gns.user.movies.constants.UserMovieServicesConstants;

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
		exceptionJsonObject.put(UserMovieServicesConstants.STATUS_CODE, 500);
		exceptionJsonObject.put(UserMovieServicesConstants.MESSAGE, e.getMessage());
		return exceptionJsonObject;
	}
}
