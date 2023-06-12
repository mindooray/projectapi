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
public class ProjectGetDTO {
    private long projectId;
    private String title;
    private String description;

    public static ProjectGetDTO projectToProjectGetDTO(Project project) {
        return new ProjectGetDTO(project.getProjectId(), project.getTitle(), project.getDescription());
    }
}
