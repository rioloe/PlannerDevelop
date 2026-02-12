package com.planner.User.service;

import com.planner.User.dto.UserGetAllResponse;
import com.planner.User.dto.UserSaveRequest;
import com.planner.User.dto.UserSaveResponse;
import com.planner.User.entity.User;
import com.planner.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 유저 생성
    @Transactional
    public UserSaveResponse save(UserSaveRequest request){
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        User savedUser = userRepository.save(user);

        return new UserSaveResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    // 유저 전체 조회
    @Transactional(readOnly = true)
    public List<UserGetAllResponse> findAll(){
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserGetAllResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreatedAt()
                )).toList();
    }
}
