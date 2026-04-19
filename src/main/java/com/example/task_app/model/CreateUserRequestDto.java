package com.example.task_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {
	
	private String firstName;
	
	private String lastName;
	
	private String username;	
	
	private String password;
	
	private String email;

}
