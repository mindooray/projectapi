package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.TaskTagId> {
}
