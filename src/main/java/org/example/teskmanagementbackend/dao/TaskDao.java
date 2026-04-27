package org.example.teskmanagementbackend.dao;

import java.util.List;

import org.example.teskmanagementbackend.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TaskDao extends JpaRepository<Task, Long>, JpaSpecificationExecutor<Task> {
	
	@Query("select t from Task t where t.user.username = :username")
	List<Task> listTaskByUsername(String username);
}

