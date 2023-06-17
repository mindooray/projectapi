package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.comment.CommentGetDTO;
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
@RequestMapping("/project-api")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/tasks/{taskId}/comments")
    public ResponseEntity<List<CommentGetDTO>> getAllComments(
            @PathVariable("taskId") Long taskId
    ) {
        return ResponseEntity.ok().body(commentService.getTaskComments(taskId));
    }

    @PostMapping("/tasks/{taskId}/comments")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentPostDTO commentPostDTO, @PathVariable Long taskId) {
        return ResponseEntity.ok().body(commentService.createComment(commentPostDTO,taskId));
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentUpdateDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateDTO commentUpdateDTO) {
        Comment updatedComment = commentService.updateComment(commentId, commentUpdateDTO);
        CommentUpdateDTO commentUpdatedDTO =new CommentUpdateDTO(updatedComment.getContent());
        return ResponseEntity.ok().body(commentUpdatedDTO);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }
}

