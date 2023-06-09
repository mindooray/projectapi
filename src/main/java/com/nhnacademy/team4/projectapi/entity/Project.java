package com.nhnacademy.team4.projectapi.entity;

import com.nhnacademy.team4.projectapi.entity.type.ProjectStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "project")

public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "project",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY)
    private List<AccountProject> accountProjectList;
    public Project() {
        this.accountProjectList = new ArrayList<>();
    }

    @Builder
    public Project(String title, ProjectStatus status, String description) {
        this.title = title;
        this.status = status;
        this.createDate = LocalDateTime.now();
        this.description = description;
        accountProjectList=new ArrayList<>();
    }

    public void addAccountProjects(List<AccountProject> accountProjectList) {
        this.accountProjectList.addAll(accountProjectList);
    }

    public void deleteAccountProject(AccountProject accountProject) {
        accountProjectList.removeIf(a -> a.equals(accountProject));
    }
}
