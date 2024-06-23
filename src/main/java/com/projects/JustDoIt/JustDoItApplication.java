package com.projects.JustDoIt;

import com.projects.JustDoIt.model.Task;
import com.projects.JustDoIt.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JustDoItApplication {

//	private static final Logger log = LoggerFactory.getLogger(JustDoItApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JustDoItApplication.class, args);
	}

}
