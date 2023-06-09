package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.milestone.MilestoneDTO;
import com.nhnacademy.team4.projectapi.entity.Milestone;
import com.nhnacademy.team4.projectapi.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks/{taskId}/milestones")
public class MilestoneController {

    private final MilestoneService milestoneService;

    @Autowired
    public MilestoneController(MilestoneService milestoneService) {
        this.milestoneService = milestoneService;
    }

    @PostMapping
    public MilestoneDTO createMilestone(@PathVariable Long taskId, @RequestBody MilestoneDTO milestoneDTO) {
        Milestone createdMilestone = milestoneService.createMilestone(taskId,milestoneDTO);
        return new MilestoneDTO(createdMilestone.getTaskId(), createdMilestone.getName(),
                createdMilestone.getStartDate(), createdMilestone.getFinishDate(),
                createdMilestone.getDeadlineStatus());
    }

    @GetMapping
    public MilestoneDTO getMilestone(@PathVariable Long taskId) {
        Milestone milestone = milestoneService.getMilestone(taskId);
        return new MilestoneDTO(milestone.getTaskId(), milestone.getName(), milestone.getStartDate(),
                milestone.getFinishDate(), milestone.getDeadlineStatus());
    }

    @PutMapping()
    public MilestoneDTO updateMilestone(@PathVariable Long taskId, @RequestBody MilestoneDTO milestoneDTO) {
        Milestone updatedMilestone = milestoneService.updateMilestone(taskId,  milestoneDTO);
        return new MilestoneDTO(updatedMilestone.getTaskId(), updatedMilestone.getName(),
                updatedMilestone.getStartDate(), updatedMilestone.getFinishDate(),
                updatedMilestone.getDeadlineStatus());
    }

    @DeleteMapping("/{taskId}")
    public void deleteMilestone(@PathVariable Long taskId ) {
        milestoneService.deleteMilestone(taskId);
    }
}
