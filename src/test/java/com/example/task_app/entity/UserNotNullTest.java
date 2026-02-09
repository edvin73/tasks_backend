package com.example.task_app.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;


class UserNotNullTest {
	
	 public static Validator validator = null;

	 @BeforeEach
	 public static void setupValidatorInstance() {
		 
		 validator = Validation.buildDefaultValidatorFactory().getValidator();
	    
	 }
	 
	 @Test
	 public void whenNotEmptyTitle_thenNoConstraintViolations() {
		 
		 // Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		 
		 Task task = new Task();
		 task.setTitle("Test Task");
		 
		 Set<ConstraintViolation<Task>> violations = validator.validate(task);
		 
		 assertThat(violations.size()).isEqualTo(0);
	      
	 }
	 
	 @Test
	 public void whenEmptyTitle_thenOneConstraintViolation() {
		 
		 // Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		 
		 Task task = new Task();
		 task.setTitle("");
		 
		 Set<ConstraintViolation<Task>> violations = validator.validate(task);
		 
		 assertThat(violations.size()).isEqualTo(1);
	 }
	 
	 @Test
	 public void whenNullTitle_thenOneConstraintViolation() {
		 
		 // Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		 
		 Task task = new Task();
		 task.setTitle(null);
		 
		 Set<ConstraintViolation<Task>> violations = validator.validate(task);
		 
		 assertThat(violations.size()).isEqualTo(1);
	 }

}
