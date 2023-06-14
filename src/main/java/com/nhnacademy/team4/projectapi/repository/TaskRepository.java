package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
