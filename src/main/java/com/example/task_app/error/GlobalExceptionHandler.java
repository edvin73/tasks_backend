package com.example.task_app.error;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.validation.ConstraintViolationException; 

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleTaskNotFoundException(TaskNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorMessage> handleConstraintViolationException(ConstraintViolationException ex) {
		
		List<String> errorMessages = ex.getConstraintViolations().stream()
				.map(violation -> violation.getMessage())
				.collect(Collectors.toList());
		
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), errorMessages.toString(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ErrorMessage> handleNoResourceFoundException(NoResourceFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorMessage> handleBadRequestException(BadRequestException ex) {
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(OptimisticLockingException.class)
	public ResponseEntity<ErrorMessage> handleObjectOptimisticLockingFailureException(OptimisticLockingException ex) {
		
		ErrorMessage errorMessage =  new ErrorMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.CONFLICT.value());
		
		return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(FieldValidationException.class)
	public ResponseEntity<ErrorMessage> handleFieldValidationException(FieldValidationException ex) {
		
		ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
		
		return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
	}
}
