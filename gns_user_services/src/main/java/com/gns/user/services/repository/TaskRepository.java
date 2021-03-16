package com.gns.user.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gns.user.services.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

}
