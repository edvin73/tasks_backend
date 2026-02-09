package com.example.task_app.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.task_app.entity.Task;

@Component
public class TaskMapper {
	
	public TaskDTO mapTaskToDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setTaskId(task.getTaskId());
		taskDTO.setTitle(task.getTitle());
		taskDTO.setAccomplished(task.getAccomplished());
		
		return taskDTO;
	}
	
	public List<TaskDTO> mapTaskListToDTOList(List<Task> tasks) {
		List<TaskDTO> taskDTOs = new ArrayList<>();
		
		tasks.forEach(task -> {
			TaskDTO taskDTO = mapTaskToDTO(task);
			taskDTOs.add(taskDTO);
		});
		
		
		return taskDTOs;
	}
	
	public Task mapTaskDTOToTask(TaskDTO taskDTO) {
		Task task = new Task();
		task.setTaskId(taskDTO.getTaskId());
		task.setTitle(taskDTO.getTitle());
		task.setAccomplished(taskDTO.getAccomplished());
		
		return task;
	}	
	
	public List<Task> mapTaskDTOListToTaskList(List<TaskDTO> taskDTOs) {
		List<Task> tasks = new ArrayList<>();
		
		taskDTOs.forEach(taskDTO -> {
			Task task = mapTaskDTOToTask(taskDTO);
			tasks.add(task);
		});
		
		return tasks;
	}
}
