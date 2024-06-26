package com.projects.JustDoIt.controller;

import com.projects.JustDoIt.mappers.Mapper;
import com.projects.JustDoIt.model.dto.TaskDto;
import com.projects.JustDoIt.model.enitities.Task;
import com.projects.JustDoIt.service.TaskService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping(path = "/tasks")
    public List<TaskDto> listTasks() {
        List<Task> tasks = taskService.findAll();
        return tasks.stream()
                .map(taskMapper::mapTo)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") Long id) {
        Optional<Task> foundOneTask = taskService.findOneTask(id); // optional entity since we may or may not find a task by that id
        return foundOneTask.map(taskEntity -> {
            TaskDto taskDto = taskMapper.mapTo(taskEntity);
            return new ResponseEntity<>(taskDto, HttpStatus.OK); // response is ok if we found the task
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // else return not found
    }
}

