package com.example.task_app.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.task_app.entity.User;

@Component
public class UserMapper {
	
	private final TaskMapper taskMapper;
	
	public UserMapper(TaskMapper taskMapper) {
		this.taskMapper = taskMapper;
	}
	
	public UserDto mapUserToDTO(User user) {
		if (user == null) {
			return null;
		}
		
		UserDto userDto = new UserDto();
		userDto.setUserId(user.getUserId());
		userDto.setFirstName(user.getFirstName());
		userDto.setLastName(user.getLastName());
		userDto.setUsername(user.getUsername());
		userDto.setEmail(user.getEmail());
		userDto.setRole(user.getRole());
		
		if(user.getTasks() != null) {
			userDto.setTasks(taskMapper.mapTasksToDtoMinList(user.getTasks()));
		}
		
		return userDto;
	}
	
	public List<UserDto> mapUsersToDTOs(List<User> users) {
		if (users == null) {
			return null;
		}
		
		List<UserDto> userDtos = new ArrayList<>();
		for (User user : users) {
			userDtos.add(mapUserToDTO(user));
		}
		
		return userDtos;
	}
	
	public UserDtoMin mapUserToDtoMin(User user) {
		if (user == null) {
			return null;
		}
		
		UserDtoMin userDtoMin = new UserDtoMin();
		userDtoMin.setUserId(user.getUserId());
		userDtoMin.setUsername(user.getUsername());
		userDtoMin.setRole(user.getRole());
		
		return userDtoMin;
	}
	
	public List<UserDtoMin> mapUsersToDtoMinList(List<User> users) {
		if (users == null) {
			return null;
		}
		
		List<UserDtoMin> userDtosMin = new ArrayList<>();
		for (User user : users) {
			userDtosMin.add(mapUserToDtoMin(user));
		}
		
		return userDtosMin;
	}
	
	public User mapDTOToUser(UserDto userDto) {
		if (userDto == null) {
			return null;
		}
		
		User user = new User();
		user.setUserId(userDto.getUserId());
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		
		return user;
	}
	
	public User mapCreateUserToUser(CreateUserRequestDto createUserRequestDto) {
		if (createUserRequestDto == null) {
			return null;
		}
		
		User user = new User();
		user.setFirstName(createUserRequestDto.getFirstName());
		user.setLastName(createUserRequestDto.getLastName());
		user.setUsername(createUserRequestDto.getUsername());
		user.setPassword(createUserRequestDto.getPassword());
		user.setEmail(createUserRequestDto.getEmail());
		
		return user;
	}
}
