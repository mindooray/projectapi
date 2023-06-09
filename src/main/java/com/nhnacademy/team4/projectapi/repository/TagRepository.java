package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface TagRepository extends JpaRepository<Tag, Long> {
}
