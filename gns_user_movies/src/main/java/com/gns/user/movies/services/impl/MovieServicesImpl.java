package com.gns.user.movies.services.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gns.user.movies.constants.UserMovieServicesConstants;
import com.gns.user.movies.exception.ExceptionHandlerUtil;
import com.gns.user.movies.model.Movie;
import com.gns.user.movies.repository.MovieRepository;
import com.gns.user.movies.service.MovieServices;

@Service
public class MovieServicesImpl implements MovieServices {
	private static final Logger LOGGER = LogManager.getLogger(MovieServicesImpl.class);

	@Autowired
	MovieRepository movieRepository;

	@Override
	public JSONObject createMovies(List<Movie> movies) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			movieRepository.saveAll(movies);
			responseJsonObject.put(UserMovieServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserMovieServicesConstants.MESSAGE, "Created/Updated Movies Successfully");
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return responseJsonObject;
	}

	@Override
	public List<Movie> getAllMovies(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Movie> pagedResult = movieRepository.findAll(paging);
		return pagedResult.getContent();
	}

	@Override
	public JSONObject deleteMovies(List<Long> movieIds) {
		JSONObject responseJsonObject = new JSONObject();
		try {
			movieIds.stream().forEach(movieId -> movieRepository.deleteById(movieId));
			responseJsonObject.put(UserMovieServicesConstants.STATUS_CODE, 200);
			responseJsonObject.put(UserMovieServicesConstants.MESSAGE, "Deleted Movies Successfully");
		} catch (Exception e) {
			LOGGER.error(e);
			responseJsonObject = ExceptionHandlerUtil.getInstance().getExceptionJSON(e);
		}
		return responseJsonObject;
	}

}
