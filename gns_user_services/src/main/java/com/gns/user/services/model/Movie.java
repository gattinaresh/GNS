package com.gns.user.services.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "user_movies", uniqueConstraints = { @UniqueConstraint(columnNames = { "classPK", "movieName" }) })
public class Movie {

	@Column(name = "movieId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "classPK")
	private Long classPK;

	@Column(name = "movieName")
	@NotBlank
	private String name;

	@Column(name = "movieRating")
	@Range(min = 1, max = 5)
	private int rating;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClassPK() {
		return classPK;
	}

	public void setClassPK(Long classPK) {
		this.classPK = classPK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}