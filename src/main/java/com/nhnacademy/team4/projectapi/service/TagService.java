package com.nhnacademy.team4.projectapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.dto.tag.TagDTO;
import com.nhnacademy.team4.projectapi.repository.TagRepository;

@Service
public class TagService {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setName(tagDTO.getName());
        return tagRepository.save(tag);
    }

    public Tag getTag(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + tagId));
    }

    public Tag updateTag(Long tagId, TagDTO tagDTO) {
        Tag existingTag = getTag(tagId);
        existingTag.setName(tagDTO.getName());
        return tagRepository.save(existingTag);
    }

    public void deleteTag(Long tagId) {
        Tag existingTag = getTag(tagId);
        tagRepository.delete(existingTag);
    }
}
