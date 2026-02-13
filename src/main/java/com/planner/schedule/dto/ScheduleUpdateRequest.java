package com.planner.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleUpdateRequest {
    private String title;
    private String content;
    private String password;
}