package com.gns.user.services.constants;

public class UserServicesConstants {

	public static final  String USER_NAME_KEY = "userName";
	public static final  String USER_PASSWORD_KEY = "userPassword";
	public static final  String USER_EMAIL_ADDRESS_KEY = "userEmailAddress";
	public static final  String USER_CREATED_BY_KEY = "userCreatedBy";
	
	public static final  String STATUS_CODE = "statusCode";
	public static final  String MESSAGE = "message";
	public static final  String RESULT = "result";
	public static final  String STATUS_SUCCESS = "success";
	public static final  String STATUS_FAILURE = "fail";
	public static final  String START_KEY = "start";
	public static final  String END_KEY = "end";
	
	public static final String USER_MOVIE_SERVICES_BASE_URL_KEY = "gns.user.movies.service.base.url";
	
	public static final String SECRET = "Gns_key_Secret";
	public static final String JWT_TOKEN  = "jwtToken";
	public static final long EXPIRATIONTIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String IS_USER_AUTHENTICATED = "isUserAuthenticated";

	private UserServicesConstants() {}
}
