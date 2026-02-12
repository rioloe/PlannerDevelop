package com.planner.User.controller;

import com.planner.User.dto.*;
import com.planner.User.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 유저 단건 조회
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserGetResponse> getOne(
            @PathVariable Long userId // URL 경로에 있는 {userId}를 받아옵니다.
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findOne(userId));
    }

    // 유저 수정
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserUpdateResponse> update(
            @PathVariable Long userId,
            @RequestBody UserUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }
}
