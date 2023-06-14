package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.project.AccountIdDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectAccountPostDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectPostDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectUpdateDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagGetDTO;
import com.nhnacademy.team4.projectapi.entity.AccountProject;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.entity.type.AccountProjectRole;
import com.nhnacademy.team4.projectapi.entity.type.ProjectStatus;
import com.nhnacademy.team4.projectapi.repository.AccountProjectRepository;
import com.nhnacademy.team4.projectapi.repository.ProjectRepository;
import com.nhnacademy.team4.projectapi.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    private final ProjectRepository projectRepository = mock(ProjectRepository.class);
    private final TagRepository tagRepository = mock(TagRepository.class);
    private final AccountProjectRepository accountProjectRepository = mock(AccountProjectRepository.class);
    private final ProjectService projectService = new ProjectService(projectRepository, tagRepository, accountProjectRepository);

    @Test
    void createProject() {
        // Mock 데이터 설정
        ProjectPostDTO projectPostDTO = new ProjectPostDTO();
        projectPostDTO.setTitle("New Project");
        projectPostDTO.setDescription("Project Description");
        projectPostDTO.setAccountId(1L);

        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("New Project");
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setDescription("Project Description");
        project.setCreateDate(LocalDateTime.now());

        AccountProject accountProject = AccountProject.builder()
                .accountId(1L)
                .project(project)
                .role(AccountProjectRole.ADMIN)
                .build();

        project.setAccountProjectList(new ArrayList<>(List.of(accountProject)));

        // Mock 객체 및 응답 값 설정
        given(projectRepository.save(any(Project.class))).willReturn(project);

        // 테스트 수행
        Project actual = projectService.createProject(projectPostDTO);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(project);
    }

    @Test
    void getProjectExistProjectId() {
        // Mock 데이터 설정
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("Project");
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setDescription("Project Description");
        project.setCreateDate(LocalDateTime.now());

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.of(project));

        // 테스트 수행
        Project actual = projectService.getProject(1L);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(project);
    }

    @Test
    void getProjectNotExistProjectId() {
        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.empty());

        // 예외 검증
        Assertions.assertThatThrownBy(() -> projectService.getProject(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Project not found with ID: 1");
    }

    @Test
    void updateProject() {
        // Mock 데이터 설정
        ProjectUpdateDTO projectUpdateDTO = new ProjectUpdateDTO();
        projectUpdateDTO.setTitle("Updated Project");
        projectUpdateDTO.setStatus(ProjectStatus.IN_PROGRESS.name());
        projectUpdateDTO.setDescription("Updated Description");

        Project existingProject = new Project();
        existingProject.setProjectId(1L);
        existingProject.setTitle("Project");
        existingProject.setStatus(ProjectStatus.IN_PROGRESS);
        existingProject.setDescription("Project Description");

        Project updatedProject = new Project();
        updatedProject.setProjectId(1L);
        updatedProject.setTitle("Updated Project");
        updatedProject.setStatus(ProjectStatus.IN_PROGRESS);
        updatedProject.setDescription("Updated Description");

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.of(existingProject));
        given(projectRepository.save(any(Project.class))).willReturn(updatedProject);

        // 테스트 수행
        Project actual = projectService.updateProject(1L, projectUpdateDTO);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(updatedProject);
    }

    @Test
    void updateProjectNotExist() {
        // Mock 데이터 설정
        ProjectUpdateDTO projectUpdateDTO = new ProjectUpdateDTO();
        projectUpdateDTO.setTitle("Updated Project");
        projectUpdateDTO.setStatus("COMPLETED");
        projectUpdateDTO.setDescription("Updated description");

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.empty());

        // 테스트 수행
        Assertions.assertThatThrownBy(() -> projectService.updateProject(1L, projectUpdateDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Project not found with ID: 1");

        // 결과 검증
        verify(projectRepository, times(1)).findById(anyLong());
        verify(projectRepository, never()).save(any(Project.class));
    }


    @Test
    void getProjectsByAccountId() {
        // Mock 데이터 설정
        List<Project> projects = new ArrayList<>();
        Project project1 = new Project();
        project1.setProjectId(1L);
        project1.setTitle("Project 1");
        project1.setStatus(ProjectStatus.IN_PROGRESS);
        project1.setDescription("Project 1 Description");

        Project project2 = new Project();
        project2.setProjectId(2L);
        project2.setTitle("Project 2");
        project2.setStatus(ProjectStatus.IN_PROGRESS);
        project2.setDescription("Project 2 Description");

        projects.add(project1);
        projects.add(project2);

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findAllProjectsByAccountId(anyLong())).willReturn(projects);

        // 테스트 수행
        List<Project> actual = projectService.getProjectsByAccountId(1L);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(projects);
    }

    @Test
    void getAccountIdByProjectId() {
        // Mock 데이터 설정
        Project project = new Project();
        project.setProjectId(1L);

        List<AccountProject> accountProjects = new ArrayList<>();
        AccountProject accountProject1 = AccountProject.builder()
                .accountId(1L)
                .project(project)
                .role(AccountProjectRole.ADMIN)
                .build();

        AccountProject accountProject2 = AccountProject.builder()
                .accountId(2L)
                .project(project)
                .role(AccountProjectRole.MEMBER)
                .build();

        accountProjects.add(accountProject1);
        accountProjects.add(accountProject2);

        project.setAccountProjectList(accountProjects); // Mock 객체에 AccountProject 설정

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.of(project));

        // 테스트 수행
        List<AccountIdDTO> actual = projectService.getAccountIdByProjectId(1L);

        // 결과 검증
        Assertions.assertThat(actual.get(0).getAccountId()).isEqualTo(1L);
        Assertions.assertThat(actual.get(1).getAccountId()).isEqualTo(2L);
    }


    @Test
    void getAccountIdByProjectIdNotExistProjectId() {
        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.empty());

        // 예외 검증
        Assertions.assertThatThrownBy(() -> projectService.getAccountIdByProjectId(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Project not found With ID: 1");
    }

    @Test
    void getProjectTags() {
        // Mock 데이터 설정
        List<Tag> tagList = new ArrayList<>();
        Tag tag1 = new Tag();
        tag1.setTagId(1L);
        tag1.setName("Tag 1");

        Tag tag2 = new Tag();
        tag2.setTagId(2L);
        tag2.setName("Tag 2");

        tagList.add(tag1);
        tagList.add(tag2);

        // Mock 객체 및 응답 값 설정
        given(tagRepository.findByProjectId(anyLong())).willReturn(tagList);

        // 테스트 수행
        List<TagGetDTO> actual = projectService.getProjectTags(1L);

        // 결과 검증
        Assertions.assertThat(actual).hasSize(2);
        Assertions.assertThat(actual.get(0).getTagId()).isEqualTo(1L);
        Assertions.assertThat(actual.get(0).getTagName()).isEqualTo("Tag 1");
        Assertions.assertThat(actual.get(1).getTagId()).isEqualTo(2L);
        Assertions.assertThat(actual.get(1).getTagName()).isEqualTo("Tag 2");
    }

    @Test
    void addProjectAccountsNullAccountIds() {
        // Mock 데이터 설정
        Project project = new Project();
        project.setProjectId(1L);

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.of(project));

        ProjectAccountPostDTO projectAccountPostDTO = new ProjectAccountPostDTO();
        projectAccountPostDTO.setAccountIds(null);

        // 테스트 수행 및 예외 검증
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            projectService.addProjectAccounts(1L, projectAccountPostDTO);
        });

        // 결과 검증
        verify(projectRepository, times(1)).findById(anyLong());
    }



    @Test
    void addProjectAccountsEmptyAccountIds() {
        // Mock 데이터 설정
        Project project = new Project();
        project.setProjectId(1L);

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.of(project));

        ProjectAccountPostDTO projectAccountPostDTO = new ProjectAccountPostDTO();
        projectAccountPostDTO.setAccountIds(Collections.emptyList());

        // 테스트 수행 및 예외 검증
        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> {
            projectService.addProjectAccounts(1L, projectAccountPostDTO);
        });

        // 결과 검증
        verify(projectRepository, times(1)).findById(anyLong());
    }

    // # Todo 손봐야됨
    @Test
    void addProjectAccountsValidAccountIds() {
        // Mock 데이터 설정
        Project project = new Project();
        project.setProjectId(1L);

        List<Long> accountIds = List.of(2L, 3L);

        List<AccountProject> accountProjectList = new ArrayList<>();
        AccountProject accountProject2 = AccountProject.builder()
                .accountId(2L)
                .project(project)
                .role(AccountProjectRole.MEMBER)
                .build();

        AccountProject accountProject3 = AccountProject.builder()
                .accountId(3L)
                .project(project)
                .role(AccountProjectRole.MEMBER)
                .build();

        accountProjectList.add(accountProject2);
        accountProjectList.add(accountProject3);

        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.of(project));
        given(accountProjectRepository.saveAllAndFlush(anyList())).willReturn(accountProjectList);

        // 테스트 수행
        ProjectAccountPostDTO projectAccountPostDTO = new ProjectAccountPostDTO();
        projectAccountPostDTO.setAccountIds(accountIds);
        projectService.addProjectAccounts(1L, projectAccountPostDTO);

        // 결과 검증
        verify(projectRepository, times(1)).findById(anyLong());
    }



    @Test
    void addProjectAccountsNotExistProjectId() {
        // Mock 객체 및 응답 값 설정
        given(projectRepository.findById(anyLong())).willReturn(Optional.empty());

        // 예외 검증
        Assertions.assertThatThrownBy(() -> projectService.addProjectAccounts(1L, new ProjectAccountPostDTO()))
                .isInstanceOf(IllegalArgumentException.class);

        // 결과 검증
        verify(projectRepository, times(1)).findById(anyLong());
        verify(accountProjectRepository, never()).saveAll(anyList());
    }


    @Test
    void deleteAccountProject(){
        // Mock 데이터 설정
        Project project = new Project();
        project.setProjectId(1L);

        AccountProject accountProject = AccountProject.builder()
                .accountId(1L)
                .project(project)
                .role(AccountProjectRole.MEMBER)
                .build();

        // Mock 객체 및 응답 값 설정
        given(accountProjectRepository.findById(any(AccountProject.AccountProjectId.class))).willReturn(Optional.of(accountProject));
        doNothing().when(accountProjectRepository).delete(any(AccountProject.class));

        // 테스트 수행
        projectService.deleteAccountProject(1L, 1L);

        // 결과 검증
        verify(accountProjectRepository, times(1)).findById(any(AccountProject.AccountProjectId.class));
        verify(accountProjectRepository, times(1)).delete(any(AccountProject.class));
    }

    @Test
    void deleteAccountProjectNotExist() {
        // Mock 객체 및 응답 값 설정
        given(accountProjectRepository.findById(any(AccountProject.AccountProjectId.class))).willReturn(Optional.empty());

        // 테스트 수행
        Assertions.assertThatThrownBy(() -> projectService.deleteAccountProject(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Project not found with ID: 1");

        // 결과 검증
        verify(accountProjectRepository, times(1)).findById(any(AccountProject.AccountProjectId.class));
        verify(accountProjectRepository, never()).delete(any(AccountProject.class));
    }

}
