package com.projects.JustDoIt.service.impl;

import com.projects.JustDoIt.model.enitities.Task;
import com.projects.JustDoIt.repository.TaskRepository;
import com.projects.JustDoIt.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAll() {
        return StreamSupport.stream(taskRepository
                    .findAll()
                    .spliterator(),
                    false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findOneTask(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return !taskRepository.existsById(id);
    }

    @Override
    public Task partialUpdate(Long id, Task task) {
        task.setId(id);
        return taskRepository.findById(id).map(exisitingTask -> {
            Optional.ofNullable(task.getTitle()).ifPresent(exisitingTask::setTitle);
            Optional.ofNullable(task.getDescription()).ifPresent(exisitingTask::setDescription);
            Optional.ofNullable(task.getFinished()).ifPresent(exisitingTask::setFinished);
            return taskRepository.save(exisitingTask);
        }).orElseThrow(() -> new RuntimeException("Task does not exist"));
    }

}
