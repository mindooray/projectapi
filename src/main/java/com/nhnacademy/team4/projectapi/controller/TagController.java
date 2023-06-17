package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.tag.TagDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagPostDTO;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project-api")

public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/projects/{projectId}/tags")
    public ResponseEntity<Void> createTag(
            @PathVariable("projectId") Long projectId,
            @RequestBody TagPostDTO tagPostDTO
    ) {
        tagService.createProjectTag(projectId, tagPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/tags/{tagId}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long tagId, @RequestBody TagPostDTO tagPostDTO) {
        Tag updatedTag = tagService.updateTag(tagId, tagPostDTO);
        TagDTO tagDto= new TagDTO(updatedTag.getTagId(), updatedTag.getName());
        return ResponseEntity.ok().body(tagDto) ;
    }

    @DeleteMapping("/tags/{tagId}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long tagId) {

        tagService.deleteTag(tagId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
