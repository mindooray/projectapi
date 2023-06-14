package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.tag.TagGetDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TagPostDTO;
import com.nhnacademy.team4.projectapi.dto.tag.TaskTagPostDTO;
import com.nhnacademy.team4.projectapi.entity.*;
import com.nhnacademy.team4.projectapi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final ProjectTagRepository projectTagRepository;
    private final TaskRepository taskRepository;
    private final TaskTagRepository taskTagRepository;

    @Transactional
    public Tag createProjectTag(Long projectId, TagPostDTO tagPostDTO) {
        Tag tag = new Tag();
        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        tag.setName(tagPostDTO.getName());
        Tag saveTag = tagRepository.save(tag);

        projectTagRepository.save(ProjectTag.builder()
                .project(project)
                .tag(tag)
                .build());

        return saveTag;
    }

    @Transactional
    public void addTaskTags(Long taskId, TaskTagPostDTO taskTagPostDTO) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow();
        List<Tag> tag = tagRepository.findAllByTagIdIn(taskTagPostDTO.getTagIds());

        List<TaskTag> taskTagList = tag.stream()
                .map(t -> TaskTag.builder()
                        .task(task)
                        .tag(t)
                        .build())
                .collect(Collectors.toList());

        taskTagRepository.saveAll(taskTagList);
    }

    public Tag getTag(Long tagId) {
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag not found with ID: " + tagId));
    }

    @Transactional
    public Tag updateTag(Long tagId, TagPostDTO tagPostDTO) {
        Tag existingTag = getTag(tagId);
        existingTag.setName(tagPostDTO.getName());
        return existingTag;
    }

    @Transactional
    public void deleteTag(Long tagId) {
        Tag existingTag = getTag(tagId);
        tagRepository.delete(existingTag);
    }

    public List<TagGetDTO> getTaskTags(Long taskId) {
        List<Tag> tagList = tagRepository.findAllByTaskTagList_Task_TaskId(taskId);

        return tagList.stream()
                .map(t -> new TagGetDTO(t.getTagId(), t.getName()))
                .collect(Collectors.toList());
    }
}
