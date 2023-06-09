package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.task.TaskGetDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskTitleListDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.service.TaskService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<TaskTitleListDTO>> getAllTask() {
        return ResponseEntity.ok().body(taskService.getAllTask());
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskGetDTO>  getTask(@PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseEntity.ok(TaskGetDTO.taskToTaskGetDTO(task));
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId, @RequestBody TaskUpdateDTO taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskPostDTO> postTask( @RequestBody TaskPostDTO taskPostDTO){
        Task task = taskService.createTask( taskPostDTO);
        return ResponseEntity.ok().body(TaskPostDTO.taskToTaskPostDTO(task));
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }
}