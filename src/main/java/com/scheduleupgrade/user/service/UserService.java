package com.scheduleupgrade.user.service;

import com.scheduleupgrade.user.dto.*;
import com.scheduleupgrade.user.entity.User;
import com.scheduleupgrade.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //생성 기능
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        User user = new User(request.getUserName(), request.getEmail(), request.getPassword());
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedDate(),
                savedUser.getLastModifiedDate()
        );
    }

    //전체 조회 기능
    @Transactional(readOnly = true)
    public List<GetAllUserResponse> getAll() {
        List<User> users = userRepository.findAll();
        List<GetAllUserResponse> dtos = new ArrayList<>();
        for (User user : users) {
            GetAllUserResponse dto = new GetAllUserResponse(
                    user.getId(),
                    user.getUserName(),
                    user.getEmail(),
                    user.getCreatedDate(),
                    user.getLastModifiedDate()
            );
            dtos.add(dto);
        }
        return dtos;
    }

    //선택 조회 기능
    @Transactional(readOnly = true)
    public GetOneUserResponse getOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        return new GetOneUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getCreatedDate(),
                user.getLastModifiedDate()
        );
    }

    //수정 기능
    @Transactional
    public UpdateUserResponse update(Long userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );

        user.update(request.getUserName(), request.getEmail());
        return new UpdateUserResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getLastModifiedDate()

        );
    }

    //삭제 기능
    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if (!existence) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }
        userRepository.deleteById(userId);
    }
}
