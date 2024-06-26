package com.projects.JustDoIt.controller;

import com.projects.JustDoIt.mappers.Mapper;
import com.projects.JustDoIt.model.dto.TaskDto;
import com.projects.JustDoIt.model.enitities.Task;
import com.projects.JustDoIt.service.TaskService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Task savedTask = taskService.save(task);
        return new ResponseEntity<>(taskMapper.mapTo(savedTask), HttpStatus.CREATED);
    }

    @GetMapping(path = "/tasks")
    public Page<TaskDto> listTasks(Pageable pageable) {
        Page<Task> tasks = taskService.findAll(pageable);
        return tasks.map(taskMapper::mapTo);
    }

    @GetMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable("id") Long id) {
        Optional<Task> foundOneTask = taskService.findOneTask(id); // optional entity since we may or may not find a task by that id
        return foundOneTask.map(taskEntity -> {
            TaskDto taskDto = taskMapper.mapTo(taskEntity);
            return new ResponseEntity<>(taskDto, HttpStatus.OK); // response is ok if we found the task
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // else return not found
    }

    @PutMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDto> updateFullTask(
            @PathVariable("id") Long id,
            @RequestBody TaskDto taskDto) {

        if(taskService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskDto.setId(id);
        Task task = taskMapper.mapFrom(taskDto);
        Task updatedTask = taskService.save(task);
        return new ResponseEntity<>(
                taskMapper.mapTo(updatedTask),
                HttpStatus.OK);
    }

    @PatchMapping(path = "/tasks/{id}")
    public ResponseEntity<TaskDto> updatePartialTask(
            @PathVariable("id") Long id,
            @RequestBody TaskDto taskDto) {

        if(taskService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task task = taskMapper.mapFrom(taskDto);
        Task partiallyUpdatedTask = taskService.partialUpdate(id, task);
        return new ResponseEntity<>(
                taskMapper.mapTo(partiallyUpdatedTask),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/tasks/{id}")
    public ResponseEntity deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}

