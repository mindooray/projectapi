package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ProjectRepository extends JpaRepository<Project, Long> {
}