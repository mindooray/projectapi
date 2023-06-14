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
    void getAllTask() throws Exception {
        // Mock 객체 및 응답 값 설정
        Project project = new Project();
        project.setProjectId(1L);

        Task task1 = new Task();
        task1.setTaskId(1L);
        task1.setTitle("Task 1");
        task1.setAccountId(1L);
        task1.setProject(project);
        task1.setStatus(TaskStatus.IN_PROGRESS);

        Task task2 = new Task();
        task2.setTaskId(2L);
        task2.setTitle("Task 2");
        task2.setAccountId(1L);
        task2.setProject(project);
        task2.setStatus(TaskStatus.IN_PROGRESS);

        TaskTitleListDTO task1DTO = new TaskTitleListDTO();
        task1DTO.setTaskId(task1.getTaskId());
        task1DTO.setTitle(task1.getTitle());
        task1DTO.setAccountId(task1.getAccountId());
        task1DTO.setProjectName(task1.getProject().getTitle());
        task1DTO.setStatus(task1.getStatus().toString());

        TaskTitleListDTO task2DTO = new TaskTitleListDTO();
        task2DTO.setTaskId(task2.getTaskId());
        task2DTO.setTitle(task2.getTitle());
        task2DTO.setAccountId(task2.getAccountId());
        task2DTO.setProjectName(task2.getProject().getTitle());
        task2DTO.setStatus(task2.getStatus().toString());


        List<TaskTitleListDTO> tasks = Arrays.asList(task1DTO, task2DTO);

        given(taskService.getAllTask()).willReturn(tasks);

        // 테스트 수행
        mockMvc.perform(get("/projects/{projectId}/tasks", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].taskId").value(task1.getTaskId()))
                .andExpect(jsonPath("$[0].title").value(task1.getTitle()))
                .andExpect(jsonPath("$[1].taskId").value(task2.getTaskId()))
                .andExpect(jsonPath("$[1].title").value(task2.getTitle()));
    }

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(createdTask.getTaskId()))
                .andExpect(jsonPath("$.title").value(createdTask.getTitle()));
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