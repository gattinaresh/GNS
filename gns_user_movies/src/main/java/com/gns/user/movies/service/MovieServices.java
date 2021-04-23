package com.gns.user.movies.service;

import java.util.List;

import org.json.JSONObject;

import com.gns.user.movies.model.Movie;

public interface MovieServices {
	public JSONObject createMovies(List<Movie> movies);
	public List<Movie> getAllMovies(int pageNo, int pageSize);
	public JSONObject deleteMovies(List<Long> movieIds);
}
