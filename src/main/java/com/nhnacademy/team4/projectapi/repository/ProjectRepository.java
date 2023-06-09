package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;


public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("select p from Project p inner join AccountProject ap where ap.id.accountId = ?1")
    List<Project> findAllProjectsByAccountId(Long accountId);
}