package com.nhnacademy.team4.projectapi.dto.task;

import com.nhnacademy.team4.projectapi.entity.Task;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskPostDTO {
    private long taskId;
    private long accountId;
    private String title;
    private String content;
    private String milestone;
    private LocalDate milestoneStartDate;
    private LocalDate milestoneEndDate;
    private Boolean deadlineStatus;
    private List<Long> tags;

    public static TaskPostDTO taskToTaskPostDTO(Task task,String milestone,LocalDate milestoneStartDate,LocalDate milestoneEndDate,Boolean deadlineStatus, List<Long> tags) {
        return new TaskPostDTO(task.getTaskId(), task.getAccountId(), task.getTitle(), task.getContent(), milestone, milestoneStartDate, milestoneEndDate, deadlineStatus,tags);
    }
}
