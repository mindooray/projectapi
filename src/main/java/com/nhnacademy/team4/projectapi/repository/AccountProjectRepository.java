package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.AccountProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountProjectRepository extends JpaRepository<AccountProject, AccountProject.AccountProjectId> {
    Optional<Object> findByProject_projectIdAndAccount_AccountId(Long projectId, Long accountId);
}
