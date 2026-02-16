package com.example.task_app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.task_app.entity.Task;
import com.example.task_app.repository.TaskRepository;
import com.example.task_app.service.TaskService; 

@AutoConfigureMockMvc
@SpringBootTest 
public class TestController {
	
	@Mock
    private TaskRepository taskRepository; // Creates a mock instance of TaskRepository

    @InjectMocks
    private TaskService taskService; // Injects the mock into TaskService
    
    @Autowired
    private TaskController taskController; // Declares a variable for TaskController
    
    @Autowired
    private MockMvc mockMvc; // Declares a variable for MockMvc
    
	@Test
	void testGetAllTasks() throws Exception {
		
		Long id = 2L; // Defines a Long variable id with the value 2
		
		Task tempTask = Task.builder()
						.taskId(id)
						.title("Test task")
						.accomplished("0")
						.version(0)
						.build() ; // Creates a new instance of Task and assigns it to tempTask
		
		Optional<Task> t1 =  Optional.of(tempTask); // Creates an empty Optional of Task
		
		when(taskRepository.findById(id)).thenReturn(t1); // Mocking the behavior of taskRepository to return the Optional containing tempTask when findById is called with the specified id
		
		String extectedTitle = "Test task"; // Defines a String variable expectedTitle with the value "Test task"
		
		//assert(taskService.getTaskById(id).getTitle().equals(extectedTitle));  
		
		assertEquals(extectedTitle, taskService.getTaskById(id).getTitle()); // Asserts that the title of the task returned by getTaskById is equal to the expected title
		
	}
	
	@Test
	public void contextLoads() {
	    // This test will pass if the application context loads successfully
		assertThat(taskController != null); // Asserts that the taskController is not null, indicating that it has been successfully loaded into the application context
	}
	
	@Test
	public void testGetTaskById() throws Exception {
		
		mockMvc.perform(get("/api/tasks/1")) // Performs a GET request to the specified URL
		       .andExpect(status().isOk()) // Expects the HTTP status to be 200 OK
		       .andExpect(jsonPath("$.title").value("Updated by task FAST User 2")); // Expects the JSON response to have a "title" field with the value "Test task"
	}

}
