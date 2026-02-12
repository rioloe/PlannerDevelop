package com.planner.schedule.service;

import com.planner.schedule.dto.ScheduleGetAllResponse;
import com.planner.schedule.dto.ScheduleGetResponse;
import com.planner.schedule.dto.ScheduleSaveRequest;
import com.planner.schedule.dto.ScheduleSaveResponse;
import com.planner.schedule.entity.Schedule;
import com.planner.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    // 일정 생성
    @Transactional
    public ScheduleSaveResponse save(ScheduleSaveRequest request) {
        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                request.getAuthor(),
                request.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleSaveResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getAuthor(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // 전체 일정 조회
    @Transactional(readOnly = true)
    public List<ScheduleGetAllResponse> findAll(String author) {

        // author (작성자) 없는 경우, 스트림으로 작성
        if (author == null) {
            List<Schedule> schedules = scheduleRepository.findAllByOrderByModifiedAt();
            return schedules.stream()
                    .map(schedule -> new ScheduleGetAllResponse(
                            schedule.getId(),
                            schedule.getTitle(),
                            schedule.getContent(),
                            schedule.getAuthor(),
                            schedule.getCreatedAt(),
                            schedule.getModifiedAt()
                    )).toList();
        }

        List<Schedule> schedulesByAuthor = scheduleRepository.findAllByAuthorOrderByModifiedAt(author);
        List<ScheduleGetAllResponse> dtos = new ArrayList<>();
        for (Schedule schedule : schedulesByAuthor) {
            dtos.add(new ScheduleGetAllResponse(
                    schedule.getId(),
                    schedule.getTitle(),
                    schedule.getContent(),
                    schedule.getAuthor(),
                    schedule.getCreatedAt(),
                    schedule.getModifiedAt()
            ));
        }
        return dtos;
    }

    // 선택 일정 조회
    @Transactional(readOnly = true)
    public ScheduleGetResponse findOne(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );
        return new ScheduleGetResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getAuthor(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

}
