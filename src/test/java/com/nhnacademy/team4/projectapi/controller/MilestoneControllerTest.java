package com.nhnacademy.team4.projectapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.team4.projectapi.dto.milestone.MilestoneDTO;
import com.nhnacademy.team4.projectapi.entity.Milestone;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.entity.type.TaskPriority;
import com.nhnacademy.team4.projectapi.service.MilestoneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MilestoneController.class)
class MilestoneControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    MilestoneService milestoneService;

    Long taskId = 1L;
    Task task = new Task();

    @Test
    void createMilestone() throws Exception {
        task.setTaskId(taskId);
        MilestoneDTO milestoneDTO = new MilestoneDTO();
        milestoneDTO.setName("Milestone 1");
        milestoneDTO.setStartDate(LocalDate.now());
        milestoneDTO.setFinishDate(LocalDate.now().plusDays(7));


        // Mock 객체 및 응답 값 설정
        Milestone createdMilestone = new Milestone();
        createdMilestone.setTask(task);
        createdMilestone.setName(milestoneDTO.getName());
        createdMilestone.setStartDate(milestoneDTO.getStartDate());
        createdMilestone.setFinishDate(milestoneDTO.getFinishDate());

        given(milestoneService.createMilestone(any(MilestoneDTO.class))).willReturn(createdMilestone);

        // 테스트 수행

        mockMvc.perform(post("/project-api/tasks/{taskId}/milestones", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(milestoneDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
//                    .andExpect(jsonPath("$.milestoneId").value(createdMilestone.getTaskId()))
//                    .andExpect(jsonPath("$.name").value(createdMilestone.getName()))
//                    .andExpect(jsonPath("$.startDate").value(createdMilestone.getStartDate().toString()))
//                    .andExpect(jsonPath("$.finishDate").value(createdMilestone.getFinishDate().toString()));
    }

    @Test
    void getMilestone() throws Exception {
        task.setTaskId(taskId);
        task.setAccountId(1L);
        task.setProject(new Project());
        task.setTitle("Task Title");
        task.setPriority(TaskPriority.HIGH);
        // Mock 객체 및 응답 값 설정
        Milestone milestone = new Milestone();
        milestone.setTaskId(1L);
        milestone.setTask(task);
        milestone.setDeadlineStatus(false);
        milestone.setName("Milestone Name");
        milestone.setStartDate(LocalDate.now());
        milestone.setFinishDate(LocalDate.now().plusDays(7));

        given(milestoneService.getMilestone(anyLong())).willReturn(milestone);

        // 테스트 수행
        mockMvc.perform(get("/project-api/tasks/{taskId}/milestones", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.taskId").value(milestone.getTaskId()))
                .andExpect(jsonPath("$.name").value(milestone.getName()))
                .andExpect(jsonPath("$.deadlineStatus").value(milestone.getDeadlineStatus()))
                .andExpect(jsonPath("$.startDate").value(milestone.getStartDate().toString()))
                .andExpect(jsonPath("$.finishDate").value(milestone.getFinishDate().toString()));
    }

    @Test
    void updateMilestone() throws Exception {
        // Mock 데이터 설정
        task.setTaskId(taskId);
        MilestoneDTO milestoneDTO = new MilestoneDTO();
        milestoneDTO.setName("Updated Milestone Name");
        milestoneDTO.setStartDate(LocalDate.now().plusDays(1));
        milestoneDTO.setFinishDate(LocalDate.now().plusDays(8));

        // Mock 객체 및 응답 값 설정
        Milestone updatedMilestone = new Milestone();
        updatedMilestone.setTask(task);
        updatedMilestone.setTaskId(taskId);
        updatedMilestone.setName(milestoneDTO.getName());
        updatedMilestone.setDeadlineStatus(false);
        updatedMilestone.setStartDate(milestoneDTO.getStartDate());
        updatedMilestone.setFinishDate(milestoneDTO.getFinishDate());

        given(milestoneService.updateMilestone(anyLong(), any(MilestoneDTO.class))).willReturn(updatedMilestone);

        // 테스트 수행
        mockMvc.perform(put("/project-api/tasks/{taskId}/milestones", taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(milestoneDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.taskId").value(updatedMilestone.getTaskId()))
                .andExpect(jsonPath("$.name").value(updatedMilestone.getName()))
                .andExpect(jsonPath("$.startDate").value(updatedMilestone.getStartDate().toString()))
                .andExpect(jsonPath("$.finishDate").value(updatedMilestone.getFinishDate().toString()))
                .andExpect(jsonPath("$.deadlineStatus").value(updatedMilestone.getDeadlineStatus()));
    }


    @Test
    void deleteMilestone() throws Exception {
        // Mock 데이터 설정
        Long taskId = 1L;

        // 테스트 수행
        mockMvc.perform(delete("/project-api/tasks/{taskId}/milestones", taskId))
                .andExpect(status().isOk())
                .andDo(print());
    }
}