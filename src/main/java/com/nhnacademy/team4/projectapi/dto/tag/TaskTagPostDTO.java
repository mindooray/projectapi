package com.nhnacademy.team4.projectapi.dto.tag;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class TaskTagPostDTO {
    private List<Long> tagIds;
}
