package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskTitleListDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Project;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.entity.type.TaskStatus;
import com.nhnacademy.team4.projectapi.repository.ProjectRepository;
import com.nhnacademy.team4.projectapi.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
    }

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
        return taskRepository.save(task);
    }

    public List<TaskTitleListDTO> getAllTask() {
        List<TaskTitleListDTO> taskTitleList= new ArrayList<>();
        List<Task> tasks = taskRepository.findAll();
        for (Task task : tasks) {
            taskTitleList.add(new TaskTitleListDTO(task.getTaskId(), task.getAccountId(), task.getTitle(), task.getProject().getTitle(), task.getStatus().toString()));
        }
        return taskTitleList;
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + taskId));
    }

    public Task updateTask(Long taskId, TaskUpdateDTO taskDTO) {
        Task existingTask = getTask(taskId);
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setContent(taskDTO.getContent());
        existingTask.setModifyDate(LocalDateTime.now());
        existingTask.setStatus( TaskStatus.valueOf(taskDTO.getStatus()));
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long taskId) {
        Task existingTask = getTask(taskId);
        taskRepository.delete(existingTask);
    }
}
