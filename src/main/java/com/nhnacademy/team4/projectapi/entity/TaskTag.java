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
    @MapsId("taskId")
    @JoinColumn(name = "task_id", insertable = false, updatable = false)
    private Task task;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskTagId implements Serializable {
        private Long taskId;
        private Long tagId;
    }
}