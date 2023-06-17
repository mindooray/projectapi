package com.nhnacademy.team4.projectapi.dto.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectAccountPostDTO {
    private List<Long> accountIds;
}
