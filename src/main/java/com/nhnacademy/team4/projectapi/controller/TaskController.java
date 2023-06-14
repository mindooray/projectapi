package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.milestone.MilestoneDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskGetDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskPostDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskTitleListDTO;
import com.nhnacademy.team4.projectapi.dto.task.TaskUpdateDTO;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.service.MilestoneService;
import com.nhnacademy.team4.projectapi.service.TaskService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;
    private final MilestoneService milestoneService;

    @GetMapping
    public ResponseEntity<List<TaskTitleListDTO>> getAllTask(
            @PathVariable("projectId") Long projectId
    ) {
        return ResponseEntity.ok().body(taskService.getProjectTasks(projectId));
    }

    @PostMapping
    public ResponseEntity<TaskPostDTO> postTask(@RequestBody TaskPostDTO taskPostDTO, @PathVariable Long projectId) {
        Task task = taskService.createTask(taskPostDTO, projectId);
        if (taskPostDTO.getMilestone().equals("yes")) {
            MilestoneDTO milestoneDTO = MilestoneDTO.taskPostDtoToMilestoneDTO(taskPostDTO, task.getTaskId());
            milestoneService.createMilestone(milestoneDTO);
        }
        return ResponseEntity.ok().body(taskPostDTO);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskGetDTO> getTask(@PathVariable Long taskId) {
        Task task = taskService.getTask(taskId);
        return ResponseEntity.ok(TaskGetDTO.taskToTaskGetDTO(task));
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