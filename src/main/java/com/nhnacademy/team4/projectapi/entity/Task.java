package com.nhnacademy.team4.projectapi.entity;

import com.nhnacademy.team4.projectapi.entity.type.TaskPriority;
import com.nhnacademy.team4.projectapi.entity.type.TaskStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;
@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "title")
    private String title;

    @Column(name = "priority")
    @Enumerated(EnumType.STRING)
    private TaskPriority priority;

    @Column(name = "content")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

//    @OneToMany(mappedBy = "task", cascade = CascadeType.REMOVE)
//    private List<Comment> comment;

}
