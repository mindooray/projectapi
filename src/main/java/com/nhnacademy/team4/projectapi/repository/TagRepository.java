package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByTagIdIn(List<Long> tags);

    @Query("select t from Tag t inner join ProjectTag pt on t = pt.tag " +
            "inner join Project p on pt.project = p " +
            "where p.projectId = ?1")
    List<Tag> findByProjectId(Long projectId);
    List<Tag> findAllByTagIdIn(List<Long> tagIds);
    List<Tag> findAllByTaskTagList_Task_TaskId(Long taskId);
}
