package com.nhnacademy.team4.projectapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.team4.projectapi.dto.project.AccountIdDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectGetDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectPostDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagGetDTO;
import com.nhnacademy.team4.projectapi.entity.AccountProject;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.entity.type.AccountProjectRole;
import com.nhnacademy.team4.projectapi.entity.type.ProjectStatus;
import com.nhnacademy.team4.projectapi.service.ProjectService;
import com.nhnacademy.team4.projectapi.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
@ExtendWith(SpringExtension.class)
@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ProjectService projectService;
    @MockBean
    TagService tagService;


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
        // Mock 데이터 설정
        Project project1 = Project.builder()
                .title("title1")
                .status(ProjectStatus.IN_PROGRESS)
                .description("description1")
                .build();
        project1.setProjectId(1L);

        Project project2 = Project.builder()
                .title("title2")
                .status(ProjectStatus.IN_PROGRESS)
                .description("description2")
                .build();
        project2.setProjectId(2L);

        AccountProject accountProject1 = AccountProject.builder()
                .accountId(1L)
                .project(project1)
                .role(AccountProjectRole.ADMIN)
                .build();

        AccountProject accountProject2 = AccountProject.builder()
                .accountId(1L)
                .project(project2)
                .role(AccountProjectRole.ADMIN)
                .build();

        List<Project> projects = Arrays.asList(project1, project2);
        List<ProjectGetDTO> projectGetDTOS = projects.stream()
                .map(ProjectGetDTO::projectToProjectGetDTO)
                .collect(Collectors.toList());

        // Mock 서비스 응답 설정
        given(projectService.getProjectsByAccountId(anyLong())).willReturn(projects);

        // API 호출 및 응답 검증
        mockMvc.perform(get("/projects")
                        .param("accountId", "1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].projectId").value(1L))
                .andExpect(jsonPath("$[0].title").value("title1"))
                .andExpect(jsonPath("$[0].description").value("description1"))
                .andExpect(jsonPath("$[1].projectId").value(2L))
                .andExpect(jsonPath("$[1].title").value("title2"))
                .andExpect(jsonPath("$[1].description").value("description2"));


    }

    @Test
    void getAccountIdByProjectId() throws Exception {
        Project project1 = Project.builder()
                .title("title1")
                .status(ProjectStatus.IN_PROGRESS)
                .description("description1")
                .build();
        project1.setProjectId(1L);

        AccountProject accountProject1 = AccountProject.builder()
                .accountId(1L)
                .project(project1)
                .role(AccountProjectRole.ADMIN)
                .build();

        List<AccountIdDTO> accountIds = Collections.singletonList(new AccountIdDTO(1L));

        given(projectService.getAccountIdByProjectId(anyLong())).willReturn(accountIds);

        mockMvc.perform(get("/projects/{projectId}/accounts", 1L))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].accountId").value(1L));
    }


    @Test
    void getProjectTags() throws Exception {
        // Mock 데이터 설정
        long projectId = 1L;

        // Mock 객체 및 응답 값 설정
        Tag tag1 = new Tag();
        tag1.setTagId(1L);
        tag1.setName("Tag 1");

        Tag tag2 = new Tag();
        tag2.setTagId(2L);
        tag2.setName("Tag 2");
        TagGetDTO tagGetDTO1 = new TagGetDTO(1L, "Tag 1");
        TagGetDTO tagGetDTO2 = new TagGetDTO(2L, "Tag 2");
        List<TagGetDTO> tags = Arrays.asList(tagGetDTO1, tagGetDTO2);

        given(projectService.getProjectTags(projectId)).willReturn(tags);

        // 테스트 수행
        mockMvc.perform(get("/projects/{projectId}/tags", projectId))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$[0].tagId").value(tag1.getTagId()))
                .andExpect(jsonPath("$[0].tagName").value(tag1.getName()))
                .andExpect(jsonPath("$[1].tagId").value(tag2.getTagId()))
                .andExpect(jsonPath("$[1].tagName").value(tag2.getName()));
    }


    @Test
    void createProject() throws Exception {
        // Mock 데이터 설정
        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
        projectPostDTO.setTitle("Project Title");
        projectPostDTO.setDescription("Project Description");
        projectPostDTO.setAccountId(1L);

        // Mock 객체 및 응답 값 설정
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle(projectPostDTO.getTitle());
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setDescription(projectPostDTO.getDescription());
        project.setCreateDate(LocalDateTime.now());

        given(projectService.createProject(any(ProjectPostDTO.class))).willReturn(project);

        // 테스트 수행
        mockMvc.perform(post("/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }


    @Test
    void updateProject() throws Exception {
        // Mock 데이터 설정
        Long projectId = 1L;
        ProjectGetDTO projectGetDTO = new ProjectGetDTO();
        projectGetDTO.setTitle("Updated Title");
        projectGetDTO.setDescription("Updated Description");

        // Mock 객체 및 응답 값 설정
        Project updatedProject = new Project();
        updatedProject.setProjectId(projectId);
        updatedProject.setTitle(projectGetDTO.getTitle());
        updatedProject.setStatus(ProjectStatus.IN_PROGRESS);
        updatedProject.setDescription(projectGetDTO.getDescription());
        updatedProject.setCreateDate(LocalDateTime.now());

        given(projectService.updateProject(anyLong(), any(ProjectGetDTO.class))).willReturn(updatedProject);

        // 테스트 수행
        mockMvc.perform(put("/projects/{projectId}", projectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(projectGetDTO)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.projectId").value(updatedProject.getProjectId()))
                .andExpect(jsonPath("$.title").value(updatedProject.getTitle()))
                .andExpect(jsonPath("$.description").value(updatedProject.getDescription()));
    }


    /**
     * # Todo
     *
     * @throws Exception
     */
    @Test
    void addProjectAccount() throws Exception {
        // Mock 데이터 설정
        Long projectId = 1L;
        List<Long> accountIds = Arrays.asList(1L, 2L, 3L);

        // 테스트 수행
        mockMvc.perform(post("/projects/{projectId}/tasks/accounts", projectId)
                        .param("accountIds", StringUtils.collectionToCommaDelimitedString(accountIds)))
                .andDo(print())
                .andExpect(status().isOk());
    }

}