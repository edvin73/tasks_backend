package com.example.task_app.error;

public class FieldValidationException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FieldValidationException(String message) {
		super(message);
	}
	
	public FieldValidationException(String fieldName, String message) {
		super("Validation failed for field '" + fieldName + "': " + message);
	}

}
