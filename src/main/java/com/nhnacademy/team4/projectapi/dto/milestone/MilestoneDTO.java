package com.nhnacademy.team4.projectapi.dto.milestone;

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

}
