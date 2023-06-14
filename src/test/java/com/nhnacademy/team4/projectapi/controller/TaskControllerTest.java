package com.nhnacademy.team4.projectapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskTitleListDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.entity.type.TaskStatus;
import com.nhnacademy.team4.projectapi.service.MilestoneService;
import com.nhnacademy.team4.projectapi.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    TaskService taskService;
    @MockBean
    MilestoneService milestoneService;

    @Test
    void postTask() throws Exception {
        // Mock 데이터 설정
        TaskPostDTO taskPostDTO = new TaskPostDTO();
        taskPostDTO.setTitle("New Task");
        taskPostDTO.setAccountId(1L);
        taskPostDTO.setMilestone("No");
        // Mock 객체 및 응답 값 설정
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("Project 1");

        Task createdTask = new Task();
        createdTask.setTaskId(1L);
        createdTask.setTitle(taskPostDTO.getTitle());
        createdTask.setAccountId(taskPostDTO.getAccountId());
        createdTask.setProject(project);

        createdTask.setStatus(TaskStatus.IN_PROGRESS);

        given(taskService.createTask(any(TaskPostDTO.class), anyLong())).willReturn(createdTask);

        // 테스트 수행
        mockMvc.perform(post("/projects/{projectId}/tasks", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskPostDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void getTask() throws Exception {
        // Mock 데이터 설정
        Long taskId = 1L;

        // Mock 객체 및 응답 값 설정
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("Project 1");

        Task task = new Task();
        task.setTaskId(taskId);
        task.setTitle("Task 1");
        task.setAccountId(1L);
        task.setProject(project);
        task.setStatus(TaskStatus.IN_PROGRESS);

        given(taskService.getTask(anyLong())).willReturn(task);

        // 테스트 수행
        mockMvc.perform(get("/projects/{projectId}/tasks/{taskId}", 1L, taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(task.getTaskId()))
                .andExpect(jsonPath("$.title").value(task.getTitle()));
    }

    @Test
    void updateTask() throws Exception {
        // Mock 데이터 설정
        Long taskId = 1L;
        TaskUpdateDTO taskDTO = new TaskUpdateDTO();
        taskDTO.setTitle("Updated Task");

        // Mock 객체 및 응답 값 설정
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("Project 1");

        Task updatedTask = new Task();
        updatedTask.setTaskId(taskId);
        updatedTask.setTitle(taskDTO.getTitle());
        updatedTask.setAccountId(1L);
        updatedTask.setProject(project);
        updatedTask.setStatus(TaskStatus.IN_PROGRESS);

        given(taskService.updateTask(anyLong(), any(TaskUpdateDTO.class))).willReturn(updatedTask);

        // 테스트 수행
        mockMvc.perform(put("/projects/{projectId}/tasks/{taskId}", 1L, taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(updatedTask.getTaskId()))
                .andExpect(jsonPath("$.title").value(updatedTask.getTitle()));
    }

    @Test
    void deleteTask() throws Exception {
        // Mock 데이터 설정
        Long taskId = 1L;

        // 테스트 수행
        mockMvc.perform(delete("/projects/{projectId}/tasks/{taskId}", 1L, taskId))
                .andExpect(status().isOk());
    }
}