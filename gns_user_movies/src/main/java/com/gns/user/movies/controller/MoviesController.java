package com.gns.user.movies.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.client.RestTemplate;

import com.gns.user.movies.constants.UserMovieServicesConstants;
import com.gns.user.movies.exception.ExceptionHandlerUtil;
import com.gns.user.movies.model.Movie;
import com.gns.user.movies.services.impl.MovieServicesImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/movies")
public class MoviesController {

	private static final Logger LOGGER = LogManager.getLogger(MoviesController.class);

	@Autowired
	MovieServicesImpl movieServices;

	@PostMapping(path = "createMovies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createMovies(@RequestBody List<Movie> movies) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = movieServices.createMovies(movies);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
			LOGGER.error(e);
			e.printStackTrace();
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@PutMapping(path = "updateMovies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateMovies(@RequestBody List<Movie> movies) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = movieServices.createMovies(movies);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
			LOGGER.error(e);
			e.printStackTrace();
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@DeleteMapping(path = "deleteMovies", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteMovies(@RequestBody List<Long> movieIds) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = movieServices.deleteMovies(movieIds);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
			LOGGER.error(e);
			e.printStackTrace();
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	@GetMapping("all")
	public ResponseEntity<String> getAllMovies(@RequestParam("start") int start, @RequestParam("end") int end) {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put(UserMovieServicesConstants.RESULT, movieServices.getAllMovies(start, end));
			jsonObject.put(UserMovieServicesConstants.STATUS_CODE, 200);
		} catch (Exception e) {
			jsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	private void rest(List<String> featureNames) {
		HttpEntity<List<String>> enHttpEntity = new HttpEntity<>(featureNames, getHeaders());
		RestTemplate restTemplate = new RestTemplate();

	}

}
