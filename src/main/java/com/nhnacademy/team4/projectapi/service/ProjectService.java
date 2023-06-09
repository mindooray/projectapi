package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.project.*;
import com.nhnacademy.team4.projectapi.dto.tag.TagGetDTO;
import com.nhnacademy.team4.projectapi.entity.AccountProject;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.entity.type.AccountProjectRole;
import com.nhnacademy.team4.projectapi.entity.type.ProjectStatus;
import com.nhnacademy.team4.projectapi.repository.AccountProjectRepository;
import com.nhnacademy.team4.projectapi.repository.ProjectRepository;
import com.nhnacademy.team4.projectapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final AccountProjectRepository accountProjectRepository;

    @Transactional
    public Project createProject(ProjectPostDTO projectPostDTO) {
        Project project = new Project();
        project.setTitle(projectPostDTO.getTitle());
        project.setStatus(ProjectStatus.IN_PROGRESS);
        project.setDescription(projectPostDTO.getDescription());
        project.setCreateDate(LocalDateTime.now());

        AccountProject accountProject = AccountProject.builder()
                .accountId(projectPostDTO.getAccountId())
                .project(project)
                .role(AccountProjectRole.ADMIN)
                .build();

        project.setAccountProjectList(new ArrayList<>(List.of(accountProject)));
        return projectRepository.save(project);
    }

    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));
    }

    @Transactional
    public Project updateProject(Long projectId, ProjectUpdateDTO projectUpdateDTO) {
        Project existingProject = getProject(projectId);
        existingProject.setTitle(projectUpdateDTO.getTitle());
        existingProject.setStatus(ProjectStatus.valueOf(projectUpdateDTO.getStatus()));
        existingProject.setDescription(projectUpdateDTO.getDescription());

        return projectRepository.save(existingProject);
    }

    public List<Project> getProjectsByAccountId(Long accountId) {
        return projectRepository.findAllProjectsByAccountId(accountId);
    }

    public List<AccountIdDTO> getAccountIdByProjectId(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found With ID: " + projectId));

        return project.getAccountProjectList().stream()
                .map(ap -> new AccountIdDTO(ap.getId().getAccountId()))
                .collect(Collectors.toList());
    }

    public List<TagGetDTO> getProjectTags(Long projectId) {
        List<Tag> tagList = tagRepository.findByProjectId(projectId);
        return tagList.stream()
                .map(t -> new TagGetDTO(t.getTagId(), t.getName()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void addProjectAccounts(Long projectId, ProjectAccountPostDTO projectAccountPostDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        List<Long> accountIds = projectAccountPostDTO.getAccountIds();
        accountIds = Optional.ofNullable(accountIds)
                .orElseThrow(() -> new IllegalArgumentException("Account IDs cannot be null"));

        List<AccountProject> accountProjectList = accountIds.stream()
                .map(a -> AccountProject.builder()
                        .accountId(a)
                        .project(project)
                        .role(AccountProjectRole.MEMBER)
                        .build())
                .collect(Collectors.toList());

        accountProjectList = Optional.of(accountProjectList)
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalArgumentException("Account project list cannot be empty"));

        project.addAccountProjects(accountProjectList);
    }



    @Transactional
    public void deleteAccountProject(Long projectId, Long accountId) {
        AccountProject accountProjectId = accountProjectRepository.findById(new AccountProject.AccountProjectId(accountId, projectId))
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        accountProjectRepository.delete(accountProjectId);
    }

    public ProjectRoleDTO getProjectRole(Long projectId, Long accountId) {
        AccountProject accountProject = accountProjectRepository.findById(new AccountProject.AccountProjectId(accountId, projectId))
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));

        return new ProjectRoleDTO(accountProject.getRole().toString());
    }
}
