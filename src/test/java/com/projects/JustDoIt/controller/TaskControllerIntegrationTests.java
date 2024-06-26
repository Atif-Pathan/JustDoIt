package com.projects.JustDoIt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.JustDoIt.TestDataUtil;
import com.projects.JustDoIt.model.enitities.Task;
import com.projects.JustDoIt.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TaskControllerIntegrationTests {

    private TaskService taskService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public TaskControllerIntegrationTests(MockMvc mockMvc, TaskService taskService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.taskService = taskService;
    }

    @Test
    public void testThatCreateTaskSuccessfullyReturnsHttp201Created() throws Exception {
        Task testA = TestDataUtil.createTestTaskA();
        testA.setId(null);
        String testJson = objectMapper.writeValueAsString(testA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateTaskSuccessfullyReturnsSavedTask() throws Exception {
        Task testA = TestDataUtil.createTestTaskA();
        testA.setId(null);
        String testJson = objectMapper.writeValueAsString(testA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(testJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Eat Breakfast")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("Menu: Jam/Bread + Eggs")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.finished").value(true)
        );
    }

    @Test
    public void testThatListTasksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListTasksReturnsListOfTasks() throws Exception {
        Task taskA = TestDataUtil.createTestTaskA();
        taskService.save(taskA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("Eat Breakfast")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].description").value("Menu: Jam/Bread + Eggs")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].finished").value(true)
        );
    }

    @Test
    public void testThatGetTaskReturnsHttpStatus200WhenTaskExists() throws Exception {
        Task taskA = TestDataUtil.createTestTaskA();
        taskService.save(taskA);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetTaskReturnsHttpStatus404WhenTaskNotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/5")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetTaskReturnsWhenATaskExists() throws Exception {
        Task taskC = TestDataUtil.createTestTaskC();
        taskService.save(taskC);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Nap")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("Nap at 3pm")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.finished").value(false)
        );
    }

}

