package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.task.TaskGetDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskTitleListDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{project.id}/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public List<TaskTitleListDTO> getAllTask() {
        return taskService.getAllTask();
    }

    @GetMapping("/{taskId}")
    public TaskGetDTO getTask(@PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        return new TaskGetDTO(task.getTaskId(), task.getAccountId(),task.getProject().getTitle(), task.getTitle(),
                task.getContent(),task.getStatus().toString(),task.getCreateDate());
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}
