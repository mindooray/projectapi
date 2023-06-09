package com.nhnacademy.team4.projectapi.entity;

import com.nhnacademy.team4.projectapi.entity.Task;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "milestone")
public class Milestone {
    @Id
    @Column(name = "task_id")
    private Long taskId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "task_id",referencedColumnName = "task_id")
    private Task task;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "deadline_status")
    private Boolean deadlineStatus;
}



