package com.projects.JustDoIt.controller;

import com.projects.JustDoIt.mappers.Mapper;
import com.projects.JustDoIt.model.dto.TaskDto;
import com.projects.JustDoIt.model.enitities.Task;
import com.projects.JustDoIt.service.TaskService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class TaskController {

    private TaskService taskService;

    private Mapper<Task, TaskDto> taskMapper;

    @Autowired
    public TaskController(TaskService taskService, Mapper<Task, TaskDto> taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping(path = "/tasks")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapFrom(taskDto);
        Task savedTask = taskService.createTask(task);
        return new ResponseEntity<>(taskMapper.mapTo(savedTask), HttpStatus.CREATED);
    }


//    @GetMapping(path = "/tasks")
//    public Iterable<TaskDto> getTasks() {
//        Iterable<Task> taskService.getAllTasks();
//
//    }
}

