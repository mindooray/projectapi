package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.project.AccountIdDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectAccountPostDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectGetDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectPostDTO;
import com.nhnacademy.team4.projectapi.dto.project.ProjectUpdateDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagGetDTO;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectGetDTO> getProject(@PathVariable Long projectId) {
        Project project = projectService.getProject(projectId);
        return ResponseEntity.ok().body(ProjectGetDTO.projectToProjectGetDTO(project));
    }


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
    public ResponseEntity<Void> createProject(@RequestBody ProjectPostDTO projectPostDTO) {
        Project project = projectService.createProject(projectPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectGetDTO> updateProject(@PathVariable Long projectId, @RequestBody ProjectUpdateDTO projectGetDTO) {
        Project project = projectService.updateProject(projectId, projectGetDTO);
        return ResponseEntity.ok().body(ProjectGetDTO.projectToProjectGetDTO(project));
    }

    /**
     * # Todo 1
     *
     * @param projectId
     * @param projectAccountPostDTO
     * @return
     */
    @PostMapping("/{projectId}/accounts")
    public ResponseEntity<Void> addProjectAccounts(
            @PathVariable("projectId") Long projectId,
            @RequestBody ProjectAccountPostDTO projectAccountPostDTO
    ) {
        projectService.addProjectAccounts(projectId, projectAccountPostDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{projectId}/accounts/{accountId}")
    public ResponseEntity<Void> deleteProjectAccount(
            @PathVariable("projectId") Long projectId,
            @PathVariable("accountId") Long accountId
    ) {
        projectService.deleteAccountProject(projectId, accountId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
