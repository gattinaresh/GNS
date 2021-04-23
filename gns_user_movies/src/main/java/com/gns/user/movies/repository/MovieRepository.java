package com.gns.user.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gns.user.movies.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

}
