package com.example.task_app.entity;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TASK")
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TASK")
	@SequenceGenerator(name = "SEQ_TASK", sequenceName = "SEQ_TASK", allocationSize = 1)
	@Column(name = "TASK_ID")
	public Long taskId;
	
	@Column(name = "TITLE")  
	@NotBlank(message = "Title is mandatory")
	@Size(min = 3, max = 500, message = "Title must be between 3 and 500 characters")
	public String title;
	
	@Column(name = "ACCOMPLISHED")
	public String accomplished;

}
