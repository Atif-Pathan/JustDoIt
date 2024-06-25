package com.projects.JustDoIt.service.impl;

import com.projects.JustDoIt.model.enitities.Task;
import com.projects.JustDoIt.repository.TaskRepository;
import com.projects.JustDoIt.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Optional<Task> findATask(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public Iterable<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
