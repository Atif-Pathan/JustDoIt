package com.projects.JustDoIt.service;

import com.projects.JustDoIt.model.enitities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TaskService {

    Task save(Task t);
    List<Task> findAll();
    Page<Task> findAll(Pageable pageable);
    Optional<Task> findOneTask(Long id);
    boolean isExists(Long id);
    Task partialUpdate(Long id, Task task);
    void deleteTask(Long id);
}
