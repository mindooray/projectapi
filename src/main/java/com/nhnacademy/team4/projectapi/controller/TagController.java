package com.nhnacademy.team4.projectapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.dto.tag.TagDTO;
import com.nhnacademy.team4.projectapi.service.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public TagDTO createTag(@RequestBody TagDTO tagDTO) {
        Tag createdTag = tagService.createTag(tagDTO);
        return new TagDTO(createdTag.getTagId(), createdTag.getName());
    }

    @GetMapping("/{tagId}")
    public TagDTO getTag(@PathVariable Long tagId) {
        Tag tag = tagService.getTag(tagId);
        return new TagDTO(tag.getTagId(), tag.getName());
    }

    @PutMapping("/{tagId}")
    public TagDTO updateTag(@PathVariable Long tagId, @RequestBody TagDTO tagDTO) {
        Tag updatedTag = tagService.updateTag(tagId, tagDTO);
        return new TagDTO(updatedTag.getTagId(), updatedTag.getName());
    }

    @DeleteMapping("/{tagId}")
    public void deleteTag(@PathVariable Long tagId) {
        tagService.deleteTag(tagId);
    }
}
