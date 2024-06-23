package com.projects.JustDoIt.repository;

import com.projects.JustDoIt.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {

    List<Task> findByFinished(Boolean finished);
    Task findById(long id);
}
