package com.nhnacademy.team4.projectapi.service;

import com.nhnacademy.team4.projectapi.dto.milestone.MilestoneDTO;
import com.nhnacademy.team4.projectapi.entity.Milestone;
import com.nhnacademy.team4.projectapi.entity.Task;
import com.nhnacademy.team4.projectapi.repository.MilestoneRepository;
import com.nhnacademy.team4.projectapi.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {
    @InjectMocks
    MilestoneService milestoneService;

    @Mock
    MilestoneRepository milestoneRepository;
    @Mock
    TaskRepository taskRepository;

    @Test
    void createMilestone() {
        // Mock 데이터 설정
        MilestoneDTO milestoneDTO = new MilestoneDTO();
        milestoneDTO.setTaskId(1L);
        milestoneDTO.setName("Milestone");
        milestoneDTO.setStartDate(LocalDate.of(2023, 6, 1));
        milestoneDTO.setFinishDate(LocalDate.of(2023, 6, 30));
        milestoneDTO.setDeadlineStatus(true);

        Task task = new Task();
        task.setTaskId(1L);

        Milestone milestone = new Milestone();
        milestone.setTaskId(1L);
        milestone.setTask(task);
        milestone.setName("Milestone");
        milestone.setStartDate(LocalDate.of(2023, 6, 1));
        milestone.setFinishDate(LocalDate.of(2023, 6, 30));
        milestone.setDeadlineStatus(true);

        // Mock 객체 및 응답 값 설정
        given(taskRepository.findById(anyLong())).willReturn(Optional.of(task));
        given(milestoneRepository.save(any(Milestone.class))).willReturn(milestone);

        // 테스트 수행
        Milestone actual = milestoneService.createMilestone(milestoneDTO);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(milestone);
    }

    @Test
    void getMilestone() {
        // Mock 데이터 설정
        Milestone milestone = new Milestone();
        milestone.setTaskId(1L);
        milestone.setTask(new Task());
        milestone.setName("Milestone");
        milestone.setStartDate(LocalDate.of(2023, 6, 1));
        milestone.setFinishDate(LocalDate.of(2023, 6, 30));
        milestone.setDeadlineStatus(true);

        // Mock 객체 및 응답 값 설정
        given(milestoneRepository.findById(anyLong())).willReturn(Optional.of(milestone));

        // 테스트 수행
        Milestone actual = milestoneService.getMilestone(1L);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(milestone);
    }

    @Test
    void updateMilestone() {
        // Mock 데이터 설정
        MilestoneDTO milestoneDTO = new MilestoneDTO();
        milestoneDTO.setName("Updated Milestone");
        milestoneDTO.setStartDate(LocalDate.of(2023, 7, 1));
        milestoneDTO.setFinishDate(LocalDate.of(2023, 7, 31));
        milestoneDTO.setDeadlineStatus(false);

        Milestone existingMilestone = new Milestone();
        existingMilestone.setTaskId(1L);
        existingMilestone.setTask(new Task());
        existingMilestone.setName("Milestone");
        existingMilestone.setStartDate(LocalDate.of(2023, 6, 1));
        existingMilestone.setFinishDate(LocalDate.of(2023, 6, 30));
        existingMilestone.setDeadlineStatus(true);

        Milestone updatedMilestone = new Milestone();
        updatedMilestone.setTaskId(1L);
        updatedMilestone.setTask(new Task());
        updatedMilestone.setName("Updated Milestone");
        updatedMilestone.setStartDate(LocalDate.of(2023, 7, 1));
        updatedMilestone.setFinishDate(LocalDate.of(2023, 7, 31));
        updatedMilestone.setDeadlineStatus(false);

        // Mock 객체 및 응답 값 설정
        given(milestoneRepository.findById(anyLong())).willReturn(Optional.of(existingMilestone));
        given(milestoneRepository.save(any(Milestone.class))).willReturn(updatedMilestone);

        // 테스트 수행
        Milestone actual = milestoneService.updateMilestone(1L, milestoneDTO);

        // 결과 검증
        Assertions.assertThat(actual).isEqualTo(updatedMilestone);
    }


    @Test
    void updateMilestoneNotFoundId() {
        // Mock 객체 및 응답 값 설정
        given(milestoneRepository.findById(anyLong())).willReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> milestoneService.updateMilestone(1L, new MilestoneDTO()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Milestone not found with ID: 1");
    }


    @Test
    void deleteMilestone() {
        // Mock 데이터 설정
        Milestone milestone = new Milestone();
        milestone.setTaskId(1L);
        milestone.setTask(new Task());
        milestone.setName("Milestone");
        milestone.setStartDate(LocalDate.of(2023, 6, 1));
        milestone.setFinishDate(LocalDate.of(2023, 6, 30));
        milestone.setDeadlineStatus(true);
        doNothing().when(milestoneRepository).delete(any());
        // Mock 객체 및 응답 값 설정
        given(milestoneRepository.findById(anyLong())).willReturn(Optional.of(milestone));
        // 테스트 수행
        milestoneService.deleteMilestone(1L);
        // 결과 검증
        verify(milestoneRepository, times(1)).delete(any());
    }

    @Test
    void deleteMilestoneNotFoundId() {
        // Mock 객체 및 응답 값 설정
        given(milestoneRepository.findById(anyLong())).willReturn(Optional.empty());

        // 예외 검증
        Assertions.assertThatThrownBy(() -> milestoneService.deleteMilestone(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Milestone not found with ID: 1");
    }
}
