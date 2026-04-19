package com.example.task_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.task_app.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final CustomUserDetailService customUserDetailService;
	
	public SecurityConfig(CustomUserDetailService customUserDetailService) {
		this.customUserDetailService = customUserDetailService;
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/api/tasks/**").authenticated()
				.requestMatchers("/api/authenticate").permitAll()
				.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
				.anyRequest().permitAll()
		)
		.formLogin(Customizer.withDefaults())
		.httpBasic(Customizer.withDefaults())
		;
		
		return http.build();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		var user = User.builder()
//				.username("user")
//				.password(passwordEncoder().encode("password"))
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(customUserDetailService);
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
	    		return authConfig.getAuthenticationManager();
	}
}
