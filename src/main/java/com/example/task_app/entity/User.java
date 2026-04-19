package com.example.task_app.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USER")
	@SequenceGenerator(name = "SEQ_USER", sequenceName = "SEQ_USER", allocationSize = 1)
	@Column(name = "USER_ID")
	private Long userId;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	@NotBlank(message = "Last name is mandatory")
	private String lastName;
	
	@Column(name = "USERNAME", unique = true)
	@NotBlank(message = "Username is mandatory")
	private String username;
	
	@Column(name = "PASSWORD")
	@NotBlank(message = "Password is mandatory")
	private String password;
	
	@Column(name = "EMAIL", unique = true)
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Task> tasks;
	
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;
	
	@Column(name = "LAST_MODIFIED_AT")
	private LocalDateTime lastModifiedAt;
	
	@Column(name = "ROLE")
	private String role;

}
