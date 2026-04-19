package com.example.task_app.service;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.task_app.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		var user = this.userRepository.findByUsername(username)
				 .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		
//		Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
//		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
//		authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
		 
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(),
				user.getPassword(), 
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
		);
	}

}
