package com.nhnacademy.team4.projectapi.dto.project;

import com.nhnacademy.team4.projectapi.entity.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPostDTO {
    private long accountId;
    private String title;
    private String description;

    public static ProjectPostDTO projectToProjectDTO(Project project) {
        return new ProjectPostDTO(project.getProjectId(), project.getTitle(), project.getDescription());
    }
}
