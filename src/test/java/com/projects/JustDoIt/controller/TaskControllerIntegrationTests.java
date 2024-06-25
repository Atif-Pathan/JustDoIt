package com.projects.JustDoIt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.JustDoIt.TestDataUtil;
import com.projects.JustDoIt.model.enitities.Task;
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

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public TaskControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
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

}
