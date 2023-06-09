package com.nhnacademy.team4.projectapi.repository;

import com.nhnacademy.team4.projectapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface CommentRepository extends JpaRepository<Comment, Long> {
}