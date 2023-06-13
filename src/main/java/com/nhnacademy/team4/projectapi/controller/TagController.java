package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.tag.TagDTO;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")

public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<Void> createTag(@RequestBody TagDTO tagDTO) {
        tagService.createTag(tagDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable Long tagId, @RequestBody TagDTO tagDTO) {
        Tag updatedTag = tagService.updateTag(tagId, tagDTO);
        TagDTO tagDto= new TagDTO(updatedTag.getTagId(), updatedTag.getName());
        return ResponseEntity.ok().body(tagDto) ;
    }

    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
    }
}
