package com.nhnacademy.team4.projectapi.dto.task;


import com.nhnacademy.team4.projectapi.entity.type.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskGetDTO {
    private long taskId;
    private long accountId;
    private String projectName;
    private String title;
    private String content;
    private String status;
    private LocalDateTime createDate;
}
