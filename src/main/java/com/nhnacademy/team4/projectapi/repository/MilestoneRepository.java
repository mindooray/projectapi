package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface MilestoneRepository extends JpaRepository<Milestone, Long> {
    Milestone findByTaskId(Long taskId);
}
