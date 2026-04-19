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
import com.example.task_app.model.TaskDtoMin;
import com.example.task_app.model.TaskMapper;
import com.example.task_app.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
	@Operation(summary = "Get all tasks", description = "Returns a list of all tasks with minimal details")
	@Tag(name = "Get Tasks", description = "Endpoints for retrieving tasks")
	public ResponseEntity<List<TaskDtoMin>> getAllTasks() {
		
		List<TaskDtoMin> tasks = taskMapper.mapTasksToDtoMinList(taskService.getAllTasks());
		
		return new ResponseEntity<>(tasks, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	@Tag(name = "Get Task by ID", description = "Endpoint for retrieving a task by its ID")
	public ResponseEntity<TaskDtoMin> getTaskById(@PathVariable("id") Long id) {
		
		TaskDtoMin task = taskMapper.mapTaskToDtoMin(taskService.getTaskById(id));
		
		return new ResponseEntity<>(task, HttpStatus.OK);
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<List<TaskDTO>> getTaskByUsername(@PathVariable("username") String username) {
		
		List<TaskDTO> tasks = taskMapper.mapTaskListToDTOList(taskService.getTasksByUsername(username));
		
		return new ResponseEntity<>(tasks, HttpStatus.OK);
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
	
	@GetMapping("/lock-test")
	public ResponseEntity<String> lockTest() {
		
		taskService.lockTest();
		
		return new ResponseEntity<>("Lock test completed", HttpStatus.OK);
	}
}