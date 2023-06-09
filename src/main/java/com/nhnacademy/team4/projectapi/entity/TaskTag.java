package com.nhnacademy.team4.projectapi.entity;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
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