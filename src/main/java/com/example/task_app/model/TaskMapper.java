package com.example.task_app.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.task_app.entity.Task;
import com.example.task_app.entity.User;

@Component
public class TaskMapper {
	
	public TaskDTO mapTaskToDTO(Task task) {
		TaskDTO taskDTO = new TaskDTO();
		taskDTO.setTaskId(task.getTaskId());
		taskDTO.setTitle(task.getTitle());
		taskDTO.setAccomplished(task.getAccomplished());
		
		if(task.getUser() != null) {
			UserDto userDto = new UserDto();
			userDto.setUserId(task.getUser().getUserId());
			userDto.setFirstName(task.getUser().getFirstName());
			userDto.setLastName(task.getUser().getLastName());
			userDto.setUsername(task.getUser().getUsername());
			userDto.setEmail(task.getUser().getEmail());
			
			taskDTO.setUser(userDto);
		}
		
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
		
		if(taskDTO.getUser() != null) {
			
			User user = new User();
			user.setUserId(taskDTO.getUser().getUserId());
			user.setFirstName(taskDTO.getUser().getFirstName());
			user.setLastName(taskDTO.getUser().getLastName());
			user.setUsername(taskDTO.getUser().getUsername());
			user.setEmail(taskDTO.getUser().getEmail());
			
			task.setUser(user);
			
		}
		
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
	
	public TaskDtoMin mapTaskToDtoMin(Task task) {
		
		TaskDtoMin taskDtoMin = new TaskDtoMin();
		
		taskDtoMin.setTaskId(task.getTaskId());
		taskDtoMin.setTitle(task.getTitle());
		taskDtoMin.setAccomplished(task.getAccomplished());
		
		if(task.getUser() != null) {
			UserDtoMin userDto = new UserDtoMin();
			userDto.setUserId(task.getUser().getUserId());
			userDto.setUsername(task.getUser().getUsername());
			userDto.setEmail(task.getUser().getEmail());
			userDto.setFirstName(task.getUser().getFirstName());
			userDto.setLastName(task.getUser().getLastName());
			
			taskDtoMin.setUser(userDto);
			
		}
		
		return taskDtoMin;
	}
	
	public List<TaskDtoMin> mapTasksToDtoMinList(List<Task> tasks) {
		List<TaskDtoMin> taskDtoMins = new ArrayList<>();
		
		tasks.forEach(task -> {
			TaskDtoMin taskDtoMin = mapTaskToDtoMin(task);
			taskDtoMins.add(taskDtoMin);
		});
		
		return taskDtoMins;
	}
}
