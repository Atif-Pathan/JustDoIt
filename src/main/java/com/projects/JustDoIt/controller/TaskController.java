//package com.projects.JustDoIt.controller;
//
//import com.projects.JustDoIt.model.Task;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class TaskController {
//
//    private List<Task> mockTasks = List.of(new Task("Eat Breakfast", "Eat Eggs + Bread/Jam", Boolean.FALSE));
//    @GetMapping("/")
//    public String intro() {
//        return "Hello! Welcome to the Task Manager: JustDoIt!";
//    }
//
//    @GetMapping("/tasks")
//    public List<Task> getMockTasks() {
//        return mockTasks;
//    }
//}
