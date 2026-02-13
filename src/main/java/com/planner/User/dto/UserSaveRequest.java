package com.planner.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserSaveRequest {
    @NotBlank(message = "유저명 필수")
    @Size(max = 4, message = "유저명 4글자 이내")
    private String username;
    @Email(message = "올바른 이메일 형식 아님")
    private String email;
    @Size(min = 8, message = "비밀번호 8자 이상이어야함")
    private String password;
}
