package com.nhnacademy.team4.projectapi.dto.task;


import com.nhnacademy.team4.projectapi.entity.Task;
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

    public static TaskGetDTO taskToTaskGetDTO(Task task){
        return new TaskGetDTO(
                task.getTaskId(),
                task.getAccountId(),
                task.getProject().getTitle(),
                task.getTitle(),
                task.getContent(),
                task.getStatus().toString(),
                task.getCreateDate()
        );

    }

}
