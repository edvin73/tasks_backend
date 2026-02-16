package com.example.task_app.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.example.task_app.entity.Task;
import com.example.task_app.error.FieldValidationException;
import com.example.task_app.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
@ActiveProfiles("test")
@Import(TaskService.class)
public class TaskRepositoryTest {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private TaskService taskService;
	 
	@Test
	public void testSave() {
		
		Task task = new Task();
		task.setTitle("Test Task  testSave");
		task.setAccomplished("0");
		
		Task savedTask = taskService.createTask(task);
		
		assert savedTask.getTaskId() != null;
		
	}
	
	
	@Test
	public void testFindAll() {
		
		Task task = new Task();
		task.setTitle("Test Task  testSave");
		task.setAccomplished("0");
		
		Task savedTask = taskService.createTask(task);
		
		List<Task> tasks = taskService.getAllTasks();
		
		assertThat(tasks.size() == 1);
	}
	
	@Test
	public void testNullTitle() {
		
		Task task = new Task();
		task.setAccomplished("0");
		
		try {
			taskService.createTask(task);
			fail("Expected FieldValidationException to be thrown");
		} catch (FieldValidationException e) {
			assertThat(e.getMessage()).contains("Title cannot be null or empty");
		}
		
		
		
	}

}
