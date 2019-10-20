package com.gns.user.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gns.user.services.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
