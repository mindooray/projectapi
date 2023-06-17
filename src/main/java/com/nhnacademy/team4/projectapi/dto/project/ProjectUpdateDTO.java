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
public class ProjectUpdateDTO {
    private long projectId;
    private String title;
    private String description;
    private String Status;

    public static ProjectUpdateDTO projectToProjectGetDTO(Project project) {
        return new ProjectUpdateDTO(project.getProjectId(), project.getTitle(), project.getDescription(), project.getStatus().toString());
    }
}
