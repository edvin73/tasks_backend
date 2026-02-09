package com.example.task_app.error;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
 
public record ErrorMessage(@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
										LocalDateTime timestamp, 
										String message, 
										Integer status) {
	 
}
