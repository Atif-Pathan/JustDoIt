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

	private static final Logger log = LoggerFactory.getLogger(JustDoItApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(JustDoItApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TaskRepository repository) {
		return (args) -> {
			// save a few customers
			repository.save(new Task("Breakfast", "Eat eggs + Bread/Jam", true));
			repository.save(new Task("Lunch", "Eat Shrimp", false));
			repository.save(new Task("Pray", "Pray asr at 6pm", false));
			repository.save(new Task("Nap", "Sleep from 4-5pm", false));
			repository.save(new Task("Visit Ammar", "", false));

			// fetch all customers
			log.info("Tasks found with findAll():");
			log.info("-------------------------------");
			repository.findAll().forEach(task -> {
				log.info(task.toString());
			});
			log.info("");

			// fetch an individual customer by ID
			Task task1 = repository.findById(1);
			log.info("Task found with findById(1):");
			log.info("--------------------------------");
			log.info(task1.toString());
			log.info("");

			// fetch customers by last name
			log.info("Task found with findByFinished(True):");
			log.info("--------------------------------------------");
			repository.findByFinished(true).forEach(task -> {
				log.info(task.toString());
			});
			log.info("");
		};
	}

}
