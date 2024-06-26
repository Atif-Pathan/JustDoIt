package com.projects.JustDoIt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.JustDoIt.TestDataUtil;
import com.projects.JustDoIt.model.dto.TaskDto;
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

    @Test
    public void testThatUpdateTaskReturnsHttpStatus404WhenTaskNotFound() throws Exception {
        TaskDto taskDto = TestDataUtil.createTestTaskDtoC();
        String taskDtoJson = objectMapper.writeValueAsString(taskDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateTaskReturnsHttpStatus200WhenTaskFoundAndUpdated() throws Exception {
        Task taskA = TestDataUtil.createTestTaskA();
        Task savedTaskA = taskService.save(taskA);

        TaskDto taskDto = TestDataUtil.createTestTaskDtoC();
        String taskDtoJson = objectMapper.writeValueAsString(taskDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/" + savedTaskA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testTaskFullUpdateUpdatesExistingTask() throws Exception {
        // create a task and save it to the db
        Task taskB = TestDataUtil.createTestTaskB();
        Task savedTaskA = taskService.save(taskB);
        // now get the task you want to update the existing task with
        TaskDto taskDto = TestDataUtil.createTestTaskDtoC();
        taskDto.setId(savedTaskA.getId()); // set id, though not sure if needed actually since we pass it through url anyways
        String taskDtoUpdateJson = objectMapper.writeValueAsString(taskDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/tasks/" + savedTaskA.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoUpdateJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTaskA.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(taskDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(taskDto.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.finished").value(taskDto.getFinished())
        );
    }

    @Test
    public void testThatPartialUpdateOnExistingTaskReturnsHttpStatus200() throws Exception {
        Task taskB = TestDataUtil.createTestTaskB();
        Task savedTaskB = taskService.save(taskB);

        TaskDto taskDtoB = TestDataUtil.createTestTaskDtoB();
        taskDtoB.setTitle("UPDATED TASK TITLE");
        String taskDtoJson = objectMapper.writeValueAsString(taskDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/tasks/" + savedTaskB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testPartialUpdateOnExistingTaskReturnsUpdatedTask() throws Exception {
        Task taskB = TestDataUtil.createTestTaskB();
        Task savedTaskB = taskService.save(taskB);

        TaskDto taskDtoB = TestDataUtil.createTestTaskDtoB();
        taskDtoB.setTitle("UPDATED TASK TITLE");
        String taskDtoJson = objectMapper.writeValueAsString(taskDtoB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/tasks/" + savedTaskB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedTaskB.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED TASK TITLE")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(taskDtoB.getDescription())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.finished").value(taskDtoB.getFinished())
        );
    }

    @Test
    public void testThatDeleteTaskReturnsHttpStatus204() throws Exception {
        Task taskB = TestDataUtil.createTestTaskB();
        Task savedTaskB = taskService.save(taskB);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/" + savedTaskB.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    // assuming here that deleting something that doesnt exisit will also return no content since the end result is same
    // regardless of if we delete existing or no exising, they are no there any more after DELTE
    @Test
    public void testThatDeleteTaskReturnsHttpStatus204EvenWithNonExistingTask() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/tasks/100")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}

