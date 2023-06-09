package com.nhnacademy.team4.projectapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nhnacademy.team4.projectapi.entity.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
}
