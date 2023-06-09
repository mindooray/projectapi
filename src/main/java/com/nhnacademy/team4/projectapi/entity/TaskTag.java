package com.nhnacademy.team4.projectapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "task_tag")
@AllArgsConstructor
@NoArgsConstructor
public class TaskTag {
    @EmbeddedId
    private TaskTagId id;
    @ManyToOne
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;
}

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
class TaskTagId implements Serializable {
    private Long taskId;
    private Long tagId;
}