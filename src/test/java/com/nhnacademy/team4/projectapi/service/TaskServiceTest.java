package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.entity.type.TaskPriority;
import com.nhnacademy.team4.projectapi.entity.type.TaskStatus;
import com.nhnacademy.team4.projectapi.repository.ProjectRepository;
import com.nhnacademy.team4.projectapi.repository.TagRepository;
import com.nhnacademy.team4.projectapi.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @InjectMocks
    TaskService taskService;

    @Mock
    TaskRepository taskRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    ProjectRepository projectRepository;

    @Test
    void createTaskWhenYes() {
        Project project = new Project();
        project.setProjectId(1L);

        List<Long> tagIds = List.of(1L);

        TaskPostDTO dto = new TaskPostDTO();
        dto.setAccountId(1L);
        dto.setTitle("title");
        dto.setContent("content");
        dto.setTags(tagIds);

        List<Tag> tagList = new ArrayList<>();
        Tag tag = new Tag();
        tag.setTagId(1L);
        tagList.add(tag);

        Task task = new Task();
        task.setTaskId(1L);
        task.setProject(project);
        task.setAccountId(1L);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setTitle("title");
        task.setPriority(TaskPriority.MEDIUM);
        task.setContent("content");

        given(projectRepository.findById(anyLong()))
                .willReturn(Optional.of(project));
        given(tagRepository.findByTagIdIn(anyList())).willReturn(tagList);
        given(taskRepository.save(any())).willReturn(task);

        Task actual = taskService.createTask(dto, 1L);

        Assertions.assertThat(task).isEqualTo(actual);
    }
    @Test
    void createTaskWhenNo() {
        Project project = new Project();
        project.setProjectId(1L);


        TaskPostDTO dto = new TaskPostDTO();
        dto.setAccountId(1L);
        dto.setTitle("title");
        dto.setContent("content");

        List<Tag> tagList = new ArrayList<>();
        Tag tag = new Tag();
        tag.setTagId(1L);
        tagList.add(tag);

        Task task = new Task();
        task.setTaskId(1L);
        task.setProject(project);
        task.setAccountId(1L);
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setTitle("title");
        task.setPriority(TaskPriority.MEDIUM);
        task.setContent("content");

        given(projectRepository.findById(anyLong()))
                .willReturn(Optional.of(project));
        given(taskRepository.save(any())).willReturn(task);

        Task actual = taskService.createTask(dto, 1L);

        Assertions.assertThat(task).isEqualTo(actual);
    }

    @Test
    void getAllTask() {
    }

    @Test
    void getTask() {
    }

    @Test
    void updateTask() {
    }

    @Test
    void deleteTask() {
    }
}