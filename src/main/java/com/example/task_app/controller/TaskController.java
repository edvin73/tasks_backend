package com.example.task_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
 
import com.example.task_app.model.TaskDTO;
import com.example.task_app.model.TaskMapper;
import com.example.task_app.service.TaskService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
	
	private final TaskService taskService;
	private final TaskMapper taskMapper;
	
	public TaskController(TaskService taskService, 
					TaskMapper taskMapper) {
		this.taskService = taskService;
		this.taskMapper = taskMapper;
	}
	
	@GetMapping("/")
	public ResponseEntity<List<TaskDTO>> getAllTasks() {
		
		List<TaskDTO> tasks = taskMapper.mapTaskListToDTOList(taskService.getAllTasks());
		
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") Long id) {
		
		TaskDTO task = taskMapper.mapTaskToDTO(taskService.getTaskById(id));
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO task) {
		
		log.debug("Received request to create task: {}", task);
		
		TaskDTO createdTask = taskMapper.mapTaskToDTO(taskService.createTask(taskMapper.mapTaskDTOToTask(task)));
		
		return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<TaskDTO> updateTask(@PathVariable("id") Long id,@Valid @RequestBody TaskDTO task) {
		
		TaskDTO updatedTask = taskMapper.mapTaskToDTO(taskService.updateTask(id, taskMapper.mapTaskDTOToTask(task)));
		
		return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
		
		taskService.getTaskById(id);
		
		taskService.deleteTask(id);
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}	
}