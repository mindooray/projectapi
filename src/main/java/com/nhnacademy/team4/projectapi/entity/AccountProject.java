package com.nhnacademy.team4.projectapi.entity;

import com.nhnacademy.team4.projectapi.entity.type.AccountProjectRole;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "account_project")
@AllArgsConstructor
@NoArgsConstructor
public class AccountProject {

    @EmbeddedId
    private AccountProjectId id;
    @Enumerated(EnumType.STRING)
    private AccountProjectRole role;

    @JoinColumn(name = "project_id")
    @ManyToOne
    @MapsId("projectId")
    private Project project;

    @Embeddable
    @Getter
    @Setter
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class AccountProjectId implements Serializable {
        private Long accountId;
        private Long projectId;

        @Builder
        public AccountProjectId(Long accountId, Long projectId) {
            this.accountId = accountId;
            this.projectId = projectId;
        }
    }

    @Builder
    public AccountProject(Long accountId, Project project, AccountProjectRole role) {
        this.id = new AccountProjectId(accountId, project.getProjectId());
        this.project = project;
        this.role = role;
    }
}
