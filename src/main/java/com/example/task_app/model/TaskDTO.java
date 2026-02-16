package com.example.task_app.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDTO {
	
	private Long taskId;
	
	@NotBlank(message = "Title must not be blank")
	private String title;
	
	private String accomplished;

}
