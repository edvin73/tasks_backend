package com.example.task_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.task_app.config.DatabaseConfig;

@EnableConfigurationProperties(DatabaseConfig.class)
@SpringBootApplication
public class TaskAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskAppApplication.class, args);
	}

}
