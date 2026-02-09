package com.example.task_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_app.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	List<Task> findByAccomplished(String accomplished);
	
	List<Task> findByTitleStartsWithIgnoringCase(String title);

}
