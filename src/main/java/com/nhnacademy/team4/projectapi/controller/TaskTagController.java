package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.tag.TagGetDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagPostDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TaskTagPostDTO;
import com.nhnacademy.team4.projectapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TaskTagController {
    private final TagService tagService;

    @GetMapping("/tasks/{taskId}/tags")
    public ResponseEntity<List<TagGetDTO>> getTaskTags(
            @PathVariable("taskId") Long taskId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(tagService.getTaskTags(taskId));
    }

    @PostMapping("/tasks/{taskId}/tags")
    public ResponseEntity<Void> createTaskTags(
            @PathVariable("taskId") Long taskId,
            @RequestBody TaskTagPostDTO taskTagPostDTO
            ) {
        tagService.addTaskTags(taskId, taskTagPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
