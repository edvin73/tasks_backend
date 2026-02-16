package com.example.task_app.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.task_app.entity.Task;
import com.example.task_app.repository.TaskRepository;

@ExtendWith(MockitoExtension.class) // No Spring Boot context started, pure Mockito test
public class TestMockService {
	
	@Mock
	private TaskRepository taskRepository;
	
	
	@InjectMocks
	private TaskService taskService;
	
	
	@Test
	public void createTask() {
		
		// Given
		Task task = new Task();
		task.setTitle("Mock Task");
		task.setAccomplished("0");
		
		when(taskRepository.save(any(Task.class))).thenReturn(task);
		
		// When
		Task createdTask = taskService.createTask(task);
		
		// Then
		assertThat(createdTask.getTitle()).isEqualTo("Mock Task");
		assertThat(createdTask.getAccomplished()).isEqualTo("0");
		
		verify(taskRepository).save(any(Task.class));
		
	}

}
