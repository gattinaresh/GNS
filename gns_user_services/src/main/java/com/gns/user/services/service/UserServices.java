package com.gns.user.services.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;

import com.gns.user.services.constants.UserServicesConstants;
import com.gns.user.services.model.User;
import com.gns.user.services.repository.UserRepository;
import com.gns.user.services.security.TokenService;
import com.gns.user.services.util.ExceptionHandlerUtil;
import com.gns.user.services.util.UserServicesUtil;

@Service
public class UserServices {

	private static final Logger LOGGER = LogManager.getLogger(UserServices.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	TokenService tokenService;

	@Autowired
	private Environment environment;

	public JSONObject validateAndCreateUser(User user) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			UserServicesUtil userServicesUtil = new UserServicesUtil();
			JSONArray userObjErrors = userServicesUtil.validateUserJson(user, userRepository);
			if (userObjErrors.length() > 0) {
				responseJsonObject.put(UserServicesConstants.STATUS_CODE, 400);
				responseJsonObject.put(UserServicesConstants.RESULT, userObjErrors);
			} else {
				LocalDateTime now = LocalDateTime.now();
				user.setCreatedDateTime(now);
				user.setModifiedDateTime(now);
				userRepository.saveAndFlush(user);
				responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
				responseJsonObject.put(UserServicesConstants.MESSAGE, "Created User Successfully");
			}
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			responseJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return responseJsonObject;
	}

	public List<User> getAllUser(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<User> pagedResult = userRepository.findAll(paging);
		return pagedResult.getContent();
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

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			csvRecords.forEach((csvRecord) -> {
				executorService.submit(() -> {
					User user = new User();
					user.setCreatedBy(csvRecord.get("createdBy"));
					user.setName(csvRecord.get("userName"));
					user.setPassword(csvRecord.get("password"));
					user.setEmail(csvRecord.get("userEmail"));
					jsonArray.put(validateAndCreateUser(user));
				});
			});

			try {
				System.out.println("attempt to shutdown executor");
				executorService.shutdown();
				executorService.awaitTermination(30, TimeUnit.MINUTES);
			} catch (InterruptedException e) {
				System.err.println("tasks interrupted");
			} finally {
				if (!executorService.isTerminated()) {
					System.err.println("cancel non-finished tasks");
				}
				executorService.shutdownNow();
				System.out.println("shutdown finished");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		return jsonArray;
	}

	public JSONObject exportUsers(int start, int end) {
		return null;
	}

	public JSONObject deleteUserById(long userId) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			userRepository.deleteById(userId);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserServicesConstants.MESSAGE, "Deleted Successfully");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			responseJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return responseJsonObject;
	}

	public JSONObject updateUser(User userObj) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			User user = userRepository.saveAndFlush(userObj);
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserServicesConstants.MESSAGE, "Updated Successfully");
			responseJsonObject.put(UserServicesConstants.RESULT, user);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			responseJsonObject.put(UserServicesConstants.STATUS_CODE, 500);
			responseJsonObject.put(UserServicesConstants.MESSAGE, e.getMessage());
		}
		return responseJsonObject;
	}

	public JSONObject getUserMovies(int start, int end) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			String movieServicesBaseUrl = environment.getProperty(UserServicesConstants.USER_MOVIE_SERVICES_BASE_URL_KEY);
			LOGGER.info("BASE:::::" + movieServicesBaseUrl);
			ResponseSpec responseSpec = WebClient.create(movieServicesBaseUrl).get()
					.uri(uriBuilder -> uriBuilder.path("all/")
					.queryParam(UserServicesConstants.START_KEY, start)
					.queryParam(UserServicesConstants.END_KEY, end).build())
					.retrieve();
			String res = responseSpec.bodyToMono(String.class).block();
			return new JSONObject(res);
		} catch (Exception e) {
			LOGGER.error(e);
			e.printStackTrace();
			responseJsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return responseJsonObject;
	}
}
