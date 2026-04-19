package com.example.task_app.model;

import java.util.List;

import com.example.task_app.entity.Task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDtoMin {
	
	private Long userId;
	
	private String firstName;
	
	private String lastName;
	
	private String username;
	
	private String email; 
	
	private String role;

}
