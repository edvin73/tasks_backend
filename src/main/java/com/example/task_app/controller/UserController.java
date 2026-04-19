package com.example.task_app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_app.model.CreateUserRequestDto;
import com.example.task_app.model.UserDto;
import com.example.task_app.model.UserMapper;
import com.example.task_app.service.UserService;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	private final UserService userService;
	private final UserMapper userMapper;
	
	public UserController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id) {
		log.debug("Received request to get user by id: {}", id);
		
		
		UserDto user =  this.userMapper.mapUserToDTO(this.userService.getUserById(id));
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() {
		log.debug("Received request to get all users");
		
		List<UserDto> users = this.userMapper.mapUsersToDTOs(this.userService.getAllUsers());
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequestDto userDto) {
		log.debug("Received request to create user: {}", userDto);
		
		UserDto createdUser = this.userMapper.mapUserToDTO(this.userService.createUser(this.userMapper.mapCreateUserToUser(userDto)));
		
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") Long id, 
											@Valid @RequestBody CreateUserRequestDto userDto) {
		log.debug("Received request to update user with id: {} and data: {}", id, userDto);
		
		UserDto updatedUser = this.userMapper.mapUserToDTO(this.userService.updateUser(id, this.userMapper.mapCreateUserToUser(userDto)));
		
		
		log.debug("Updated user: {}", updatedUser);
		
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
}
