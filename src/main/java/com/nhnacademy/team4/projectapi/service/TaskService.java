package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskTitleListDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Tag;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.entity.TaskTag;
import com.nhnacademy.team4.projectapi.entity.type.TaskStatus;
import com.nhnacademy.team4.projectapi.repository.ProjectRepository;
import com.nhnacademy.team4.projectapi.repository.TagRepository;
import com.nhnacademy.team4.projectapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public Task createTask(TaskPostDTO taskDTO, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow();

        Task task = new Task();
        task.setAccountId(taskDTO.getAccountId());
        task.setProject(project);
        task.setTitle(taskDTO.getTitle());
        task.setContent(taskDTO.getContent());
        task.setStatus(TaskStatus.IN_PROGRESS);
        task.setCreateDate(LocalDateTime.now());

        if(Objects.nonNull(taskDTO.getTags()) && !taskDTO.getTags().isEmpty()) {
            List<Tag> tagList = tagRepository.findByTagIdIn(taskDTO.getTags());

            List<TaskTag> taskTagList = tagList.stream()
                    .map(t -> TaskTag.builder().tag(t).task(task).build())
                    .collect(Collectors.toList());

            task.setTaskTagList(taskTagList);
        }

        return taskRepository.save(task);
    }

    public List<TaskTitleListDTO> getProjectTasks(Long projectId) {
        List<Task> taskList = taskRepository.findAllByProject_ProjectId(projectId);

        return taskList.stream()
                .map(TaskTitleListDTO::toDto)
                .collect(Collectors.toList());
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
    }

    @Transactional
    public Task updateTask(Long taskId, TaskUpdateDTO taskDTO) {
        Task existingTask = getTask(taskId);
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setContent(taskDTO.getContent());
        existingTask.setModifyDate(LocalDateTime.now());
        existingTask.setStatus(TaskStatus.valueOf(taskDTO.getStatus()));
        return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTask(Long taskId) {
        Task existingTask = getTask(taskId);
        taskRepository.delete(existingTask);
    }
}
