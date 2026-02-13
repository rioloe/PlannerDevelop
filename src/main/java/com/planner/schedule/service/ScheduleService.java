package com.planner.schedule.service;

import com.planner.User.entity.User;
import com.planner.User.repository.UserRepository;
import com.planner.schedule.dto.*;
import com.planner.schedule.entity.Schedule;
import com.planner.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    // 일정 생성
    @Transactional
    public ScheduleSaveResponse save(ScheduleSaveRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new IllegalArgumentException("존재 하지 않는 유저 ID" + request.getUserId())
        );


        Schedule schedule = new Schedule(
                request.getTitle(),
                request.getContent(),
                user,
                request.getPassword()
        );
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleSaveResponse(
                savedSchedule.getId(),
                savedSchedule.getTitle(),
                savedSchedule.getContent(),
                savedSchedule.getUser().getUsername(),
                savedSchedule.getCreatedAt(),
                savedSchedule.getModifiedAt()
        );
    }

    // 전체 일정 조회
    @Transactional(readOnly = true)
    public List<ScheduleGetAllResponse> findAll(String username) {
        List<Schedule> schedules;

        // 작성자 없는 경우
        if (username == null || username.isBlank()) {
            schedules = scheduleRepository.findAllByOrderByModifiedAtDesc();
        } else {
            schedules = scheduleRepository.findAllByUserUsernameOrderByModifiedAtDesc(username);
        }
        return schedules.stream()
                .map(schedule -> new ScheduleGetAllResponse(
                        schedule.getId(),
                        schedule.getTitle(),
                        schedule.getContent(),
                        schedule.getUser().getUsername(),
                        schedule.getCreatedAt(),
                        schedule.getModifiedAt()
                )).toList();
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
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // 일정 수정
    @Transactional
    public ScheduleUpdateResponse update(Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        // 비밀번호 체크
        if (!ObjectUtils.nullSafeEquals(schedule.getPassword(), request.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다");
        }

        schedule.updateTitleAndContent(
                request.getTitle(),
                request.getContent()
        );
        return new ScheduleUpdateResponse(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getUser().getUsername(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );
    }

    // 일정 삭제
    @Transactional
    public void delete(Long scheduleId, String password) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 일정입니다.")
        );

        // 비밀번호 불일치 할때
        if (!ObjectUtils.nullSafeEquals(password, schedule.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다");
        }

        // 비밀번호 일치할때
        scheduleRepository.deleteById(scheduleId);

    }

}
