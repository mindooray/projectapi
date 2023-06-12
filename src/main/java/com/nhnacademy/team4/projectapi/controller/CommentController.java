package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.comment.CommentPostDTO;
import com.nhnacademy.team4.projectapi.dto.comment.CommentUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Comment;
import com.nhnacademy.team4.projectapi.dto.comment.CommentDTO;
import com.nhnacademy.team4.projectapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity< List<Comment>> getAllComments() {
        return ResponseEntity.ok().body(commentService.getAllComments());
    }

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentPostDTO commentPostDTO, @PathVariable Long taskId) {
        Comment createdComment = commentService.createComment(commentPostDTO,taskId);
        CommentDTO commentDTO = new CommentDTO(createdComment.getCommentId(), createdComment.getTitle(), createdComment.getContent(), createdComment.getAccountId());
        return ResponseEntity.ok().body(commentDTO);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateDTO commentUpdateDTO) {
        Comment updatedComment = commentService.updateComment(commentId, commentUpdateDTO);
        CommentUpdateDTO commentUpdatedDTO =new CommentUpdateDTO(updatedComment.getTitle(), updatedComment.getContent());
        return ResponseEntity.ok().body(commentUpdatedDTO);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}

