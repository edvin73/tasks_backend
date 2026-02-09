package com.example.task_app.error;

public class BadRequestException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5132103944255858289L;
	

	public BadRequestException(String message) {
		super(message);
	}

}
