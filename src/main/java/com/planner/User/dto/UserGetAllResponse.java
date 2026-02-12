package com.planner.User.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserGetAllResponse {
    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;

    public UserGetAllResponse(Long id, String username, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }
}
