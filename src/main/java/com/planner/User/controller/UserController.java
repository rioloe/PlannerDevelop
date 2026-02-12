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

    // 유저 삭제
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long userId // 삭제할 유저의 ID를 주소창에서 받아옵니다.
    ) {
        // 서비스에게 삭제를 시킵니다.
        userService.delete(userId);

        // 삭제가 성공하면 "내용 없음"을 뜻하는 204 No Content 상태 코드를 보내는 게 관례입니다.
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
