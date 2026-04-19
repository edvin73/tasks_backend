package com.example.task_app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.task_app.entity.User;
import com.example.task_app.error.UserNotFoundException;
import com.example.task_app.repository.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository,
						BCryptPasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}
	
	
	public User getUserById(Long id) {
		
		return userRepository.findById(id).orElseThrow( () -> new UserNotFoundException(id));	
	}
	
	public User createUser(User user) {
		
		if(user.getUserId() != null) {
			throw new IllegalArgumentException("New user cannot have an id");
		}
		
		User newUser = new User();
		
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		newUser.setEmail(user.getEmail());
		newUser.setCreatedAt(LocalDateTime.now());
				
		return userRepository.save(newUser);
	}
	
	public User updateUser(Long id, User user) {
		
		User existingUser = this.getUserById(id);
		
		if(!existingUser.getFirstName().equals(user.getFirstName())) {
			existingUser.setFirstName(user.getFirstName());
		}
		
		if(!existingUser.getLastName().equals(user.getLastName())) {
			existingUser.setLastName(user.getLastName());
		} 
		
		if(!existingUser.getUsername().equals(user.getUsername())) {
			existingUser.setUsername(user.getUsername());
		} 
		
		if(!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
			existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		
		if(!existingUser.getEmail().equals(user.getEmail())) {
			existingUser.setEmail(user.getEmail());
		}
		
		return userRepository.save(existingUser);
	}	
	
	public User deleteUser(Long id) {
		
		User userToDelete = this.getUserById(id);
		
		userRepository.delete(userToDelete);
		
		return userToDelete;
	}

}
