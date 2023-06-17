package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.comment.CommentDTO;
import com.nhnacademy.team4.projectapi.dto.comment.CommentGetDTO;
import com.nhnacademy.team4.projectapi.dto.comment.CommentPostDTO;
import com.nhnacademy.team4.projectapi.dto.comment.CommentUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhnacademy.team4.projectapi.entity.Comment;
import com.nhnacademy.team4.projectapi.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository, TaskRepository taskRepository) {
        this.commentRepository = commentRepository;
        this.taskRepository = taskRepository;
    }

    public CommentDTO createComment(CommentPostDTO commentPostDTO, Long taskId) {
        Comment comment = new Comment();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
        comment.setTask(task);
        comment.setAccountId(commentPostDTO.getAccountId());
        comment.setContent(commentPostDTO.getContent());
        comment.setCreateDate(LocalDateTime.now());

        commentRepository.save(comment);
        return new CommentDTO(task.getProject().getProjectId());
    }

    public List<CommentGetDTO> getAllComments() {
        List<Comment> commentList = commentRepository.findAll();
        return commentList.stream()
                .map(CommentGetDTO::toDto)
                .collect(Collectors.toList());
    }

    public List<CommentGetDTO> getTaskComments(Long taskId) {
        List<Comment> commentList = commentRepository.findByTask_TaskId(taskId);
        return commentList.stream()
                .map(CommentGetDTO::toDto)
                .collect(Collectors.toList());
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
        existingComment.setContent(commentUpdateDTO.getContent());
        existingComment.setFinalModifyDate(LocalDateTime.now());
        return commentRepository.save(existingComment);
    }

    public void deleteComment(Long commentId) {
        Comment existingComment = getComment(commentId);
        commentRepository.delete(existingComment);
    }

}
