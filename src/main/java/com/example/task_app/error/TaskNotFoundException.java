package com.example.task_app.error;

public class TaskNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3064694794182005246L;

	public TaskNotFoundException(Long id) {
		super("Task with id " + id + " not found");
	}
	 
}
