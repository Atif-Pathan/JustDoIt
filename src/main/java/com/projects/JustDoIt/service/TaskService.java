package com.projects.JustDoIt.service;

import com.projects.JustDoIt.model.enitities.Task;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TaskService {

    Task createTask(Task t);
    Optional<Task> findATask(Long id);
    Iterable<Task> getAllTasks();

}
