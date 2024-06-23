package com.projects.JustDoIt.service;

import com.projects.JustDoIt.model.Task;
import com.projects.JustDoIt.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Iterable<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public Task create(Task t) {
        return taskRepository.save(t);
    }
}
