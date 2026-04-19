package com.example.task_app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import com.example.task_app.entity.Task;

import jakarta.persistence.LockModeType;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	
	List<Task> findAll();
	
	List<Task> findByAccomplished(String accomplished);
	
	List<Task> findByTitleStartsWithIgnoringCase(String title);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Optional<Task> findById(Long id);

}
