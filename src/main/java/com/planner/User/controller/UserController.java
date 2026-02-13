package com.planner.User.controller;

import com.planner.User.dto.*;
import com.planner.User.entity.User;
import com.planner.User.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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
            @Valid @RequestBody UserSaveRequest request
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
            @Valid @RequestBody UserUpdateRequest request
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

    // 로그인
    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        // 1. 유저 검증
        User user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        // 2. 세션 생성
        HttpSession session = request.getSession();

        // 3. 세션에 유저 정보 저장
        session.setAttribute("USER_ID", user.getId());

        return "로그인 성공";
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // 세션이 없으면 null 반환
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "로그아웃 성공";
    }
}
