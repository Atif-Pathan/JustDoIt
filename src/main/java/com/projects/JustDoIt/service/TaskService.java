package com.projects.JustDoIt.service;

import com.projects.JustDoIt.model.enitities.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {

    Task createTask(Task t);
    List<Task> findAll();
    Optional<Task> findOneTask(Long id);
}
