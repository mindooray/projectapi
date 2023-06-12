package com.nhnacademy.team4.projectapi.dto.milestone;

import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.entity.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneDTO {

    private long taskId;

    private String name;

    private LocalDate startDate;

    private LocalDate finishDate;

    private boolean deadlineStatus;

    public static MilestoneDTO milestoneToMilestoneDTO(Milestone milestone){
        return new MilestoneDTO(
                milestone.getTaskId(),
                milestone.getName(),
                milestone.getStartDate(),
                milestone.getFinishDate(),
                milestone.getDeadlineStatus()
        );
    }
    public static MilestoneDTO taskPostDtoToMilestoneDTO(TaskPostDTO taskPostDTO, Long taskId){
        return new MilestoneDTO(
                taskId,
                taskPostDTO.getMilestone(),
                taskPostDTO.getMilestoneStartDate(),
                taskPostDTO.getMilestoneEndDate(),
                taskPostDTO.getDeadlineStatus()
        );
    }

}
