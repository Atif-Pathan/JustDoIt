package com.projects.JustDoIt.controller;

import com.projects.JustDoIt.model.Task;
import com.projects.JustDoIt.repository.TaskRepository;
import com.projects.JustDoIt.service.TaskService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "/tasks")
    public Iterable<Task> getTasks() {
        return taskService.findAllTasks();
    }

    @PostMapping(path = "/tasks")
    public Task createTask(@RequestBody final Task t) {
        log.info("got task: " + t.toString());
        return taskService.create(t);
    }
}

