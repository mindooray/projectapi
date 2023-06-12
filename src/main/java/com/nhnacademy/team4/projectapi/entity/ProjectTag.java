package com.nhnacademy.team4.projectapi.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Table(name = "project_tag")
@NoArgsConstructor
public class ProjectTag {
    @EmbeddedId
    private ProjectTagId id;

    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class ProjectTagId implements Serializable {
        private Long projectId;
        private Long tagId;
    }

    @Builder
    public ProjectTag(Project project, Tag tag) {
        this.id = new ProjectTagId(project.getProjectId(), tag.getTagId());
        this.project = project;
        this.tag = tag;
    }
}
