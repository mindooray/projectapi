package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.type.ProjectStatus;
import com.nhnacademy.team4.projectapi.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @Test
    void getProject() throws Exception {
        Project project = Project.builder()
                .title("title")
                .status(ProjectStatus.IN_PROGRESS)
                .description("description")
                .build();
        project.setProjectId(1L);

        given(projectService.getProject(any())).willReturn(project);


        mockMvc.perform(get("/projects/{projectId}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.projectId").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    void getProjectByAccountId() throws Exception {
        Project project = Project.builder()
                .title("title")
                .status(ProjectStatus.IN_PROGRESS)
                .description("description")
                .build();
        project.setProjectId(1L);

        given(projectService.getProject(any())).willReturn(project);


        mockMvc.perform(get("/projects/{projectId}", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.projectId").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"));
    }

    @Test
    void getAccountIdByProjectId() {
    }

    @Test
    void getProjectTags() {
    }

    @Test
    void createProject() {
    }

    @Test
    void updateProject() {
    }

    @Test
    void addProjectAccount() {
    }
}