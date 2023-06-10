package com.nhnacademy.team4.projectapi.controller;

import com.nhnacademy.team4.projectapi.dto.milestone.MilestoneDTO;
import com.nhnacademy.team4.projectapi.entity.Milestone;
import com.nhnacademy.team4.projectapi.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MilestoneDTO> createMilestone(@PathVariable Long taskId, @RequestBody MilestoneDTO milestoneDTO) {
        Milestone createdMilestone = milestoneService.createMilestone(milestoneDTO);
        return ResponseEntity.ok().body(MilestoneDTO.milestoneToMilestoneDTO(createdMilestone));
    }

    @GetMapping
    public ResponseEntity<MilestoneDTO> getMilestone(@PathVariable Long taskId) {
        Milestone milestone = milestoneService.getMilestone(taskId);
        return ResponseEntity.ok().body(MilestoneDTO.milestoneToMilestoneDTO(milestone));
    }

    @PutMapping()
    public ResponseEntity<MilestoneDTO> updateMilestone(@PathVariable Long taskId, @RequestBody MilestoneDTO milestoneDTO) {
        Milestone updatedMilestone = milestoneService.updateMilestone(taskId,  milestoneDTO);
        return ResponseEntity.ok().body(MilestoneDTO.milestoneToMilestoneDTO(updatedMilestone));
    }

    @DeleteMapping("/{taskId}")
    public void deleteMilestone(@PathVariable Long taskId ) {
        milestoneService.deleteMilestone(taskId);
    }
}
