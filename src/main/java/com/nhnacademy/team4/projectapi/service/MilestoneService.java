package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.milestone.MilestoneDTO;
import com.nhnacademy.team4.projectapi.entity.Milestone;
import com.nhnacademy.team4.projectapi.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;

    @Autowired
    public MilestoneService(MilestoneRepository milestoneRepository) {
        this.milestoneRepository = milestoneRepository;
    }

    public Milestone createMilestone(MilestoneDTO milestoneDTO) {
        Milestone milestone = new Milestone();
        milestone.setTaskId(milestoneDTO.getTaskId());
        milestone.setName(milestoneDTO.getName());
        milestone.setStartDate(milestoneDTO.getStartDate());
        milestone.setFinishDate(milestoneDTO.getFinishDate());
        milestone.setDeadlineStatus(milestoneDTO.isDeadlineStatus());
        return milestoneRepository.save(milestone);
    }

    public Milestone getMilestone(Long getTaskId) {
        return milestoneRepository.findById(getTaskId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found with ID: " + getTaskId));
    }

    public Milestone updateMilestone(Long getTaskId, MilestoneDTO milestoneDTO) {
        Milestone existingMilestone = getMilestone(getTaskId);
        existingMilestone.setName(milestoneDTO.getName());
        existingMilestone.setStartDate(milestoneDTO.getStartDate());
        existingMilestone.setFinishDate(milestoneDTO.getFinishDate());
        existingMilestone.setDeadlineStatus(milestoneDTO.isDeadlineStatus());
        return milestoneRepository.save(existingMilestone);
    }

    public void deleteMilestone(Long getTaskId) {
        Milestone existingMilestone = getMilestone(getTaskId);
        milestoneRepository.delete(existingMilestone);
    }
}
