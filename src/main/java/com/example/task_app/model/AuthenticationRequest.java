package com.example.task_app.model;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1072115138298253056L;

	@NotBlank(message = "Username is mandatory")
	private String username;	
	
	@NotBlank(message = "Password is mandatory")
	private String password;

}
