package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskTitleListDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskUpdateDTO;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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
    void getTask() {
        // Mock 데이터 설정
        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("Task");
        task.setAccountId(1L);
        task.setProject(new Project());
        task.setStatus(TaskStatus.IN_PROGRESS);

        // Mock 객체 및 응답 값 설정
        given(taskRepository.findById(anyLong())).willReturn(Optional.of(task));

        // 테스트 수행
        Task actual = taskService.getTask(1L);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(task);
    }

    @Test
    void updateTaskWhenExists() {
        // Mock 데이터 설정
        TaskUpdateDTO taskDTO = new TaskUpdateDTO();
        taskDTO.setTitle("Updated Task");
        taskDTO.setContent("Updated Content");
        taskDTO.setStatus(String.valueOf(TaskStatus.IN_PROGRESS));

        Task existingTask = new Task();
        existingTask.setTaskId(1L);
        existingTask.setTitle("Task");
        existingTask.setContent("Content");
        existingTask.setStatus(TaskStatus.IN_PROGRESS);
        existingTask.setModifyDate(LocalDateTime.now());

        Task updatedTask = new Task();
        updatedTask.setTaskId(1L);
        updatedTask.setTitle("Updated Task");
        updatedTask.setContent("Updated Content");
        updatedTask.setStatus(TaskStatus.DONE);
        updatedTask.setModifyDate(LocalDateTime.now());

        // Mock 객체 및 응답 값 설정
        given(taskRepository.findById(anyLong())).willReturn(Optional.of(existingTask));
        given(taskRepository.save(any(Task.class))).willReturn(updatedTask);

        // 테스트 수행
        Task actual = taskService.updateTask(1L, taskDTO);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(updatedTask);
    }

    @Test
    void updateTaskWhenNotExists() {
        // Mock 객체 및 응답 값 설정
        given(taskRepository.findById(anyLong())).willReturn(Optional.empty());

        // 예외 검증
        Assertions.assertThatThrownBy(() -> taskService.updateTask(1L, new TaskUpdateDTO()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task not found with ID: 1");
    }


    @Test
    void deleteTask() {
        Task task = new Task();
        task.setTaskId(1L);
        doNothing().when(taskRepository).delete(any());
        given(taskRepository.findById(anyLong())).willReturn(Optional.of(task));

        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).delete(any());
    }

    @Test
    void deleteTaskNotFoundId() {
        given(taskRepository.findById(anyLong())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> taskService.deleteTask(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task not found with ID: 1");
    }

}