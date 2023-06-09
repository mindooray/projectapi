package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.entity.Comment;
import com.nhnacademy.team4.projectapi.dto.comment.CommentDTO;
import com.nhnacademy.team4.projectapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/tasks/{taskId}/comments")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }
    @GetMapping("/comments/{commentId}/account")// 이거 뭔가 이상함 체크해보자
    public long getAccount(@PathVariable Long commentId) {
        return commentService.getAccount(commentId);
    }
    @PostMapping("/tasks/{taskId}/comments")
    public CommentDTO createComment(@RequestBody CommentDTO commentDTO,@PathVariable Long taskId) {
        Comment createdComment = commentService.createComment(commentDTO,taskId);
        return new CommentDTO(createdComment.getCommentId(), createdComment.getTitle(), createdComment.getContent());
    }

    @PutMapping("/{commentId}")
    public CommentDTO updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        Comment updatedComment = commentService.updateComment(commentId, commentDTO);
        return new CommentDTO(updatedComment.getCommentId(),updatedComment.getTitle(), updatedComment.getContent());
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}

