package com.mriganka.taskmanager.TaskEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class TaskEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskEngineApplication.class, args);
	}

}
