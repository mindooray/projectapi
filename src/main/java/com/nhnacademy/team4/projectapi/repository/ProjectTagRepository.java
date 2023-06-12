package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, ProjectTag.ProjectTagId> {
}
