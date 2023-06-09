package com.nhnacademy.team4.projectapi.dto.task;

import com.nhnacademy.team4.projectapi.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskPostDTO {
    private long taskId;
    private long accountId;
    private long projectId;
    private String title;
    private String content;

    public static TaskPostDTO taskToTaskPostDTO(Task task) {
        return new TaskPostDTO(task.getTaskId(), task.getAccountId(), task.getProject().getProjectId(), task.getTitle(), task.getContent());
    }
}
