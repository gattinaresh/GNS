package com.gns.user.services.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user_remainders", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "classPK", "remainderName" }) })
public class Remainder {

	@Column(name = "remainderId")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "classPK")
	private Long classPK;

	@Column(name = "remainderName")
	@NotBlank
	private String name;

	@NotNull
	@Future
	private LocalDateTime remaindTime;

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

	public LocalDateTime getRemaindTime() {
		return remaindTime;
	}

	public void setRemaindTime(LocalDateTime remaindTime) {
		this.remaindTime = remaindTime;
	}

	@Override
	public String toString() {
		return "Remainder [id=" + id + ", classPK=" + classPK + ", name=" + name + ", remaindTime=" + remaindTime + "]";
	}
	
	
}
