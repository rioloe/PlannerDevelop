package com.planner.User.controller;

import com.planner.User.dto.UserGetAllResponse;
import com.planner.User.dto.UserSaveRequest;
import com.planner.User.dto.UserSaveResponse;
import com.planner.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    // 유저 생성
    @PostMapping("/users")
    public ResponseEntity<UserSaveResponse> create(
            @RequestBody UserSaveRequest request
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    // 유저 전체 조회
    @GetMapping("/users")
    public ResponseEntity<List<UserGetAllResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }
}
