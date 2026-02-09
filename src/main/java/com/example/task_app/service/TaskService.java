package com.example.task_app.service;

import java.util.List;
 
import org.springframework.stereotype.Service; 

import com.example.task_app.entity.Task;
import com.example.task_app.error.TaskNotFoundException;
import com.example.task_app.repository.TaskRepository;

@Service
public class TaskService {
	
	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	 
	public List<Task> getAllTasks() {
		
		List<Task> tasks = taskRepository.findAll();
		
		return tasks;
	}
	
	public Task getTaskById(Long id) {
		return taskRepository.findById(id).orElseThrow( () -> {
			throw new TaskNotFoundException(id);
		});
	}
	
	public Task createTask(Task task) {
		
		return taskRepository.save(task);
	}
	
	public Task updateTask(Long id, Task task) {
		
		int updatedFields = 0;
		
		Task taskToUpdate = getTaskById(id);
		
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
		return taskRepository.save(taskToUpdate);
	}
	
	public void deleteTask(Long id) {
		
		Task taskToDelete = getTaskById(id);
		
		taskRepository.deleteById(id);
	}
}
