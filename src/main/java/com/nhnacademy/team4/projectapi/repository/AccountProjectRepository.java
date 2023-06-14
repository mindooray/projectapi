package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.AccountProject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountProjectRepository extends JpaRepository<AccountProject, AccountProject.AccountProjectId> {
}
