package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.comment.CommentPostDTO;
import com.nhnacademy.team4.projectapi.dto.comment.CommentUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhnacademy.team4.projectapi.entity.Comment;
import com.nhnacademy.team4.projectapi.dto.comment.CommentDTO;
import com.nhnacademy.team4.projectapi.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    public Comment createComment(CommentPostDTO commentPostDTO, Long taskId) {
        Comment comment = new Comment();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
        comment.setTask(task);
        comment.setAccountId(commentPostDTO.getAccountId());
        comment.setTitle(commentPostDTO.getTitle());
        comment.setContent(commentPostDTO.getContent());
        comment.setCreateDate(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
    }
    public long getAccount(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
        return comment.getTask().getAccountId();
    }
    public Comment updateComment(Long commentId, CommentUpdateDTO commentUpdateDTO) {
        Comment existingComment = getComment(commentId);
        existingComment.setTitle(commentUpdateDTO.getTitle());
        existingComment.setContent(commentUpdateDTO.getContent());
        existingComment.setFinalModifyDate(LocalDateTime.now());
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long commentId) {
        Comment existingComment = getComment(commentId);
        commentRepository.delete(existingComment);
    }
}
