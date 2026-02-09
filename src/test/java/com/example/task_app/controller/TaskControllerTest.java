package com.example.task_app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.task_app.model.TaskDTO;
import com.example.task_app.model.TaskMapper;
import com.example.task_app.entity.Task;
import com.example.task_app.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void getAllTasks_returnsOkAndJsonArray() throws Exception {
        Task task = new Task(1L, "Title One", "0");
        TaskDTO dto = new TaskDTO(1L, "Title One", "1");

        when(taskService.getAllTasks()).thenReturn(List.of(task));
        when(taskMapper.mapTaskListToDTOList(anyList())).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/tasks/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Title One"));
    }

    @Test
    void getTaskById_returnsOkAndJson() throws Exception {
        Task task = new Task(2L, "Title Two", "YES");
        TaskDTO dto = new TaskDTO(2L, "Title Two", "YES");

        when(taskService.getTaskById(2L)).thenReturn(task);
        when(taskMapper.mapTaskToDTO(task)).thenReturn(dto);

        mockMvc.perform(get("/api/tasks/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(2))
                .andExpect(jsonPath("$.title").value("Title Two"));
    }

    @Test
    void createTask_returnsCreated() throws Exception {
        TaskDTO requestDto = new TaskDTO(null, "New Task", "0");
        Task toSave = new Task(null, "New Task", "0");
        Task saved = new Task(3L, "New Task", "0");
        TaskDTO responseDto = new TaskDTO(3L, "New Task", "0");

        when(taskMapper.mapTaskDTOToTask(any(TaskDTO.class))).thenReturn(toSave);
        when(taskService.createTask(toSave)).thenReturn(saved);
        when(taskMapper.mapTaskToDTO(saved)).thenReturn(responseDto);

        mockMvc.perform(post("/api/tasks/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.taskId").value(3))
                .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void updateTask_returnsOk() throws Exception {
        TaskDTO requestDto = new TaskDTO(null, "Updated", "1");
        Task toUpdate = new Task(null, "Updated", "1");
        Task updated = new Task(4L, "Updated", "1");
        TaskDTO responseDto = new TaskDTO(4L, "Updated", "1");

        when(taskMapper.mapTaskDTOToTask(any(TaskDTO.class))).thenReturn(toUpdate);
        when(taskService.updateTask(4L, toUpdate)).thenReturn(updated);
        when(taskMapper.mapTaskToDTO(updated)).thenReturn(responseDto);

        mockMvc.perform(patch("/api/tasks/4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(4))
                .andExpect(jsonPath("$.accomplished").value("1"));
    }

    @Test
    void deleteTask_returnsNoContent() throws Exception {
        // For delete we only need the service to not throw
        mockMvc.perform(delete("/api/tasks/5"))
                .andExpect(status().isNoContent());
    }
    
    @Test
    void deleteTask_returnsNoFound() throws Exception {
        // For delete we only need the service to not throw
        mockMvc.perform(delete("/api/tasks/20"))
                .andExpect(status().isNotFound());
    }
}
