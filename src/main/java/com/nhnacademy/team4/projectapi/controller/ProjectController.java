package com.nhnacademy.team4.projectapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.dto.project.ProjectDTO;
import com.nhnacademy.team4.projectapi.service.ProjectService;
import java.util.*;
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        Project project = projectService.createProject(projectDTO);
        return ResponseEntity.ok().body(ProjectDTO.projectToProjectDTO(project));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> getProject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        return ResponseEntity.ok().body(ProjectDTO.projectToProjectDTO(project));
    }

    // TODO #1
    @GetMapping()
    public ResponseEntity<List<ProjectDTO>> getProjectByAccountId(@RequestParam long accountId){
        List<Project> projects = projectService.getProjectsByAccountId(accountId);
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for(Project project : projects){
            projectDTOs.add(ProjectDTO.projectToProjectDTO(project));
        }
        return ResponseEntity.ok().body(projectDTOs);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long projectId, @RequestBody ProjectDTO projectDTO) {
        Project project = projectService.updateProject(projectId, projectDTO);
        return ResponseEntity.ok().body(ProjectDTO.projectToProjectDTO(project));
    }

}
