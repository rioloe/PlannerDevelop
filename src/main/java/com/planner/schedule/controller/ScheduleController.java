package com.planner.schedule.controller;

import com.planner.schedule.dto.*;
import com.planner.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 일정 생성
    @PostMapping("/schedules")
    public ResponseEntity<ScheduleSaveResponse> create(
            @RequestBody ScheduleSaveRequest request
    ) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }

    // 일정 전체 조회
    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleGetAllResponse>> getAll(
            @RequestParam(required = false) String author
    ){
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findAll(author));
    }

    // 선택 일정 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleGetResponse> getOne(
            @PathVariable Long scheduleId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.findOne(scheduleId));
    }

    // 일정 수정
    @PutMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleUpdateResponse> update(
            @PathVariable Long scheduleId,
            @RequestBody ScheduleUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(scheduleService.update(scheduleId, request));
    }

    // 일정 삭제
    @DeleteMapping("/schedules/{scheduleId}")
    public void delete(
            @PathVariable Long scheduleId,
            @RequestParam String password
    ) {
        scheduleService.delete(scheduleId, password);
    }
}
