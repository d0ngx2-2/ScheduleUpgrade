package com.scheduleupgrade.user.service;

import com.scheduleupgrade.config.PasswordEncoder;
import com.scheduleupgrade.exception.CustomException;
import com.scheduleupgrade.exception.ErrorCode;
import com.scheduleupgrade.schedule.repository.ScheduleRepository;
import com.scheduleupgrade.user.dto.*;
import com.scheduleupgrade.user.entity.User;
import com.scheduleupgrade.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입 기능
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getUserName(), request.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);
        return new CreateUserResponse(
                savedUser.getId(),
                savedUser.getUserName(),
                savedUser.getEmail(),
                savedUser.getCreatedDate(),
                savedUser.getLastModifiedDate()
        );
    }

    //로그인 기능
    @Transactional(readOnly = true)
    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.INVALID_EMAIL_INPUT)
        );
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        return new UserLoginResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getUserName() + "님 로그인에 완료되었습니다."
        );
    }

    //로그아웃 기능
    @Transactional
    public void logout(HttpSession session) {
        session.invalidate();
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
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
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
    public UpdateUserResponse update(Long userId, UpdateUserRequest request, Long loginUserId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        if (!user.getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.USER_FORBIDDEN);
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

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
    public void delete(Long userId, String password, Long loginUserId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }
        if (!user.getId().equals(loginUserId)) {
            throw new CustomException(ErrorCode.USER_FORBIDDEN);
        }

        scheduleRepository.deleteAllByUser(user);
        userRepository.deleteById(userId);
    }
}
