package com.gns.user.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gns.user.services.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(value = "SELECT CASE WHEN COUNT(userName) > 0 THEN TRUE ELSE FALSE END FROM User WHERE userName = :userName")
	boolean existsByName(@Param("userName") String userName);

	@Query(value = "SELECT CASE WHEN COUNT(userEmail) > 0 THEN TRUE ELSE FALSE END FROM User WHERE userEmail = :userEmail")
	boolean existsByEmail(@Param("userEmail") String userEmail);
}
