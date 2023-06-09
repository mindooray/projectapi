package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.entity.type.ProjectStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.dto.project.ProjectDTO;
import com.nhnacademy.team4.projectapi.repository.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setTitle(projectDTO.getTitle());
        project.setStatus(ProjectStatus.valueOf(projectDTO.getStatus()));
        project.setDescription(projectDTO.getDescription());
        project.setCreateDate(LocalDateTime.now());
        return projectRepository.save(project);
    }

    public Project getProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));
    }

    public Project updateProject(Long projectId, ProjectDTO projectDTO) {
        Project existingProject = getProject(projectId);
        existingProject.setTitle(projectDTO.getTitle());
        existingProject.setStatus(ProjectStatus.valueOf(projectDTO.getStatus()));
        existingProject.setDescription(projectDTO.getDescription());

        return projectRepository.save(existingProject);
    }
    public List<Project> getProjectsByAccountId(Long accountId) {
        return projectRepository.findAllProjectsByAccountId(accountId);
    }
//    public void deleteProject(Long projectId) {
//        Project existingProject = getProject(projectId);
//        projectRepository.delete(existingProject);
//    }

}
