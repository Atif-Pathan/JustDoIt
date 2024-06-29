package com.projects.JustDoIt.repository;

import com.projects.JustDoIt.model.enitities.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long>,
        PagingAndSortingRepository<Task,Long> {
}
