package com.planner.schedule.controller;

import com.planner.schedule.dto.ScheduleSaveRequest;
import com.planner.schedule.dto.ScheduleSaveResponse;
import com.planner.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<ScheduleSaveResponse> create(
            @RequestBody ScheduleSaveRequest request
    ) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(scheduleService.save(request));
    }
}
