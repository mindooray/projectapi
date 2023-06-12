package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.ProjectTag;
import com.nhnacademy.team4.projectapi.repository.ProjectRepository;
import com.nhnacademy.team4.projectapi.repository.ProjectTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.dto.tag.TagDTO;
import com.nhnacademy.team4.projectapi.repository.TagRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final ProjectTagRepository projectTagRepository;

    @Transactional
    public Tag createTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        Project project = projectRepository.findById(tagDTO.getProjectId())
                .orElseThrow();

        tag.setName(tagDTO.getName());
        Tag saveTag = tagRepository.save(tag);

        projectTagRepository.save(ProjectTag.builder()
                .project(project)
                .tag(tag)
                .build());

        return saveTag;
    }

    public Tag getTag(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + tagId));
    }

    @Transactional
    public Tag updateTag(Long tagId, TagDTO tagDTO) {
        Tag existingTag = getTag(tagId);
        existingTag.setName(tagDTO.getName());
        return tagRepository.save(existingTag);
    }

    @Transactional
    public void deleteTag(Long tagId) {
        Tag existingTag = getTag(tagId);
        tagRepository.delete(existingTag);
    }
}
