package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nhnacademy.team4.projectapi.entity.Task;

import java.util.List;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByProject_ProjectId(Long projectId);
}
