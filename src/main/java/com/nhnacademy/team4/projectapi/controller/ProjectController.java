package com.nhnacademy.team4.projectapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.dto.project.ProjectDTO;
import com.nhnacademy.team4.projectapi.service.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ProjectDTO createProject(@RequestBody ProjectDTO projectDTO) {
        Project createdProject = projectService.createProject(projectDTO);
        return new ProjectDTO(createdProject.getProjectId(), createdProject.getTitle(),
                createdProject.getStatus().toString(), createdProject.getDescription());
    }

    @GetMapping("/{projectId}")
    public ProjectDTO getProject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        return new ProjectDTO(project.getProjectId(), project.getTitle(), project.getStatus().toString(),
                project.getDescription());
    }

    @PutMapping("/{projectId}")
    public ProjectDTO updateProject(@PathVariable Long projectId, @RequestBody ProjectDTO projectDTO) {
        Project updatedProject = projectService.updateProject(projectId, projectDTO);
        return new ProjectDTO(updatedProject.getProjectId(), updatedProject.getTitle(),
                updatedProject.getStatus().toString(), updatedProject.getDescription());
    }

//    @DeleteMapping("/{projectId}")
//    public void deleteProject(@PathVariable Long projectId) {
//        projectService.deleteProject(projectId);
//    }
}
