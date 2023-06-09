package com.nhnacademy.team4.projectapi.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDTO {
    private long projectId;
    private String title;
    private String status;
    private String description;
}
