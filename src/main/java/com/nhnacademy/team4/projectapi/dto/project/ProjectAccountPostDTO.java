package com.nhnacademy.team4.projectapi.dto.project;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProjectAccountPostDTO {
    private List<Long> accountIds;
}
