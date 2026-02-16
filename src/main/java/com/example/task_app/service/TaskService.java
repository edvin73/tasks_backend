package com.example.task_app.service;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.example.task_app.config.DatabaseConfig;
import com.example.task_app.entity.Task;
import com.example.task_app.error.FieldValidationException;
import com.example.task_app.error.OptimisticLockingException;
import com.example.task_app.error.TaskNotFoundException;
import com.example.task_app.repository.TaskRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	private final DatabaseConfig databaseConfig;
	
	@Autowired
	private EntityManager entityManager;
	
	
	public TaskService(TaskRepository taskRepository,
				DatabaseConfig databaseConfig) {	
		this.taskRepository = taskRepository;
		this.databaseConfig = databaseConfig;
	} 
	
	public List<Task> getAllTasks() {
		
		log.info("Connecting to database with URL: {}", databaseConfig.getUrl());
		
		List<Task> tasks = taskRepository.findAll();
		
		return tasks;
	}
	
	public Task getTaskById(Long id) {
		return taskRepository.findById(id).orElseThrow( () -> {
			throw new TaskNotFoundException(id);
		});
	}
	
	public Task createTask(Task task)   {
		
		if(task.getTitle() == null || task.getTitle().isEmpty()) {
			throw new FieldValidationException("Title", "Title cannot be null or empty");
		}
		
		if(task.getTitle().length() < 3) {
			throw new FieldValidationException("Title", "Title must be at least 3 characters long");
		}
		 
		Task newTask = taskRepository.save(task);
		
		return newTask;
					
	}
	
	@Transactional
	public Task updateTask(Long id, Task task) {
		
		int updatedFields = 0;
		
		Task taskToUpdate = getTaskById(id);
		
		log.info("Current version of task to update: {}", taskToUpdate);
		log.info("New version of task to update: {}", task);
		
		if(!task.getTitle().equals(taskToUpdate.getTitle()) ) {
			taskToUpdate.setTitle(task.getTitle());
			updatedFields++;
		}
		
		if(!task.getAccomplished().equals(taskToUpdate.getAccomplished()) ) {
			taskToUpdate.setAccomplished(task.getAccomplished());
			updatedFields++;
		}
		 
		if(updatedFields == 0) {
			return taskToUpdate;
		}
		
		try {
			Task updatedTask = taskRepository.save(taskToUpdate);
			return updatedTask;
		} catch (OptimisticLockingFailureException ex) {
			throw new OptimisticLockingException("Task with id " + id + " was updated by another transaction. Please refresh and try again.");
		}
	}
	
	public void deleteTask(Long id) {
		
		Task taskToDelete = getTaskById(id);
		
		taskRepository.deleteById(id);
	}
	
	@Transactional
	public void lockTest() {
		
		Task task1 = getTaskById(1L);
		
		log.info("Task 1 loaded with title: {} and version: {}", task1.getTitle(), task1.getVersion());
		
		entityManager.detach(task1);
		
		Task task2 = getTaskById(1L);
		log.info("Task 2 loaded with title: {} and version: {}", task2.getTitle(), task2.getVersion());
		
		task2.setTitle("Updated by task FAST User 2");
		taskRepository.save(task2);
		taskRepository.flush();
		
		log.info("Task 1 tente de sauvegarder sa vieille version...");
	    task1.setTitle("Updated by SLOW User 1"); 
		
		try {
			taskRepository.save(task1);
			
			taskRepository.flush(); // FORCER l'écriture en DB pour incrémenter la version
		} catch (OptimisticLockingFailureException ex) {
			log.error("Task 1 failed to update due to optimistic locking failure: {}", ex.getMessage());
			throw new OptimisticLockingException("Task 1 failed to update due to optimistic locking failure. Please refresh and try again.");
		}
		
		
	}
	 
}
