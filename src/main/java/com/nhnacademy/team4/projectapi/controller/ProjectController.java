package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.project.AccountIdDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectPostDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.dto.project.ProjectGetDTO;
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


    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectGetDTO> getProject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        return ResponseEntity.ok().body(ProjectGetDTO.projectToProjectGetDTO(project));
    }

    // TODO #1
    @GetMapping
    public ResponseEntity<List<ProjectGetDTO>> getProjectByAccountId(@RequestParam long accountId){
        List<Project> projects = projectService.getProjectsByAccountId(accountId);
        List<ProjectGetDTO> projectGetDTOS = new ArrayList<>();
        for(Project project : projects){
            projectGetDTOS.add(ProjectGetDTO.projectToProjectGetDTO(project));
        }
        return ResponseEntity.ok().body(projectGetDTOS);
    }

    @GetMapping("/{projectId}/accounts")
    public ResponseEntity<List<AccountIdDTO>> getAccountIdByProjectId(
            @PathVariable("projectId") Long projectId
    ) {
        return ResponseEntity.ok().body(projectService.getAccountIdByProjectId(projectId));
    }

    @GetMapping("/{projectId}/tags")
    public ResponseEntity<List<TagGetDTO>> getProjectTags(
            @PathVariable("projectId") Long projectId
    ) {
        return ResponseEntity.ok().body(projectService.getProjectTags(projectId));
    }

    @PostMapping
    public ResponseEntity<ProjectGetDTO> createProject(@RequestBody ProjectPostDTO projectPostDTO) {
        Project project = projectService.createProject(projectPostDTO);
        return ResponseEntity.ok().body(ProjectGetDTO.projectToProjectGetDTO(project));
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectGetDTO> updateProject(@PathVariable Long projectId, @RequestBody ProjectGetDTO projectGetDTO) {
        Project project = projectService.updateProject(projectId, projectGetDTO);
        return ResponseEntity.ok().body(ProjectGetDTO.projectToProjectGetDTO(project));
    }

    @PostMapping("/projects/{projectId}/tasks/accounts")
    public ResponseEntity<Void> addProjectAccount(
            @PathVariable("projectId") Long projectId,
            @RequestParam("accountIds") List<Long> accountIds
    ) {
        return ResponseEntity.ok().build();
    }
}
