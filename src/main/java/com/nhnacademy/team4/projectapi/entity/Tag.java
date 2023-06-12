package com.nhnacademy.team4.projectapi.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<ProjectTag> projectTagList;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<TaskTag> taskTagList;
}
