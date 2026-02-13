package com.planner.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleSaveRequest {
    @NotBlank(message = "제목은 필수")
    @Size(max = 10, message = "제목 10글자 이내")
    private String title;
    @NotBlank(message = "내용 필수")
    private String content;
    private Long userId;
    private String password;
}
