package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.milestone.MilestoneDTO;
import com.nhnacademy.team4.projectapi.entity.Milestone;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.repository.MilestoneRepository;
import com.nhnacademy.team4.projectapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MilestoneService {

    private final MilestoneRepository milestoneRepository;
    private final TaskRepository taskRepository;



    @Transactional
    public Milestone createMilestone(MilestoneDTO milestoneDTO) {
        Milestone milestone = new Milestone();
        Task task = taskRepository.findById(milestoneDTO.getTaskId())
                .orElseThrow();
        milestone.setTask(task);
        milestone.setName(milestoneDTO.getName());
        milestone.setStartDate(milestoneDTO.getStartDate());
        milestone.setFinishDate(milestoneDTO.getFinishDate());
        milestone.setDeadlineStatus(milestoneDTO.isDeadlineStatus());
        return milestoneRepository.save(milestone);
    }


    @Transactional
    public Milestone getMilestone(Long getTaskId) {
        return milestoneRepository.findById(getTaskId)
                .orElseThrow(() -> new IllegalArgumentException("Milestone not found with ID: " + getTaskId));
    }

    @Transactional
    public Milestone updateMilestone(Long getTaskId, MilestoneDTO milestoneDTO) {
        Milestone existingMilestone = getMilestone(getTaskId);
        existingMilestone.setName(milestoneDTO.getName());
        existingMilestone.setStartDate(milestoneDTO.getStartDate());
        existingMilestone.setFinishDate(milestoneDTO.getFinishDate());
        existingMilestone.setDeadlineStatus(milestoneDTO.isDeadlineStatus());
        return milestoneRepository.save(existingMilestone);
    }

    @Transactional
    public void deleteMilestone(Long getTaskId) {
        Milestone existingMilestone = getMilestone(getTaskId);
        milestoneRepository.delete(existingMilestone);
    }
}
