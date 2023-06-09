package com.nhnacademy.team4.projectapi.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskUpdateDTO {
    private long taskId;
    private long accountId;
    private long projectId;
    private String title;
    private String content;
    private String status;
}
