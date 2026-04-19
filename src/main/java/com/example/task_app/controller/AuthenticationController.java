package com.example.task_app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_app.model.AuthenticationRequest;
import com.example.task_app.model.UserDtoMin;
import com.example.task_app.repository.UserRepository;
import com.example.task_app.service.CustomUserDetailService;
 
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")	
public class AuthenticationController {
	
	private final AuthenticationManager authManager;
	private final CustomUserDetailService userDetailsService;
	
	public AuthenticationController(AuthenticationManager authManager,
									CustomUserDetailService userDetailsService) {
		this.authManager = authManager;
		this.userDetailsService = userDetailsService;
	}
	
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@Valid @RequestBody AuthenticationRequest authRequest) {
		
		try {
			
			authManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							authRequest.getUsername(), authRequest.getPassword())
			);
			
			final UserDetails userDetails =  userDetailsService.loadUserByUsername(authRequest.getUsername());
			
			return new ResponseEntity<>("Login Success : " + userDetails.getUsername() , HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>("Login Failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
		} 
	}

}
