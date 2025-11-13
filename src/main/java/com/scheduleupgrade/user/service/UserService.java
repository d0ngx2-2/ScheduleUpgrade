package com.scheduleupgrade.user.service;

import com.scheduleupgrade.user.dto.*;
import com.scheduleupgrade.user.entity.User;
import com.scheduleupgrade.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //회원가입 기능
    @Transactional
    public CreateUserResponse save(CreateUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new IllegalArgumentException(("이미 존재하는 이메일 입니다."));
        }

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

    //로그인 기능
    @Transactional(readOnly = true)
    public UserLoginResponse login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalArgumentException("이메일이 올바르지 않습니다.")
        );
        if(!user.getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
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
    public UpdateUserResponse update(Long userId, UpdateUserRequest request, Long loginUserId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
        if (!user.getId().equals(loginUserId)) {
            throw new IllegalArgumentException("해당 유저의 권한은 없습니다.");
        }

        if(!user.getPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }
        if (!user.getId().equals(loginUserId)) {
            throw new IllegalArgumentException("해당 유저의 권한은 없습니다.");
        }
        userRepository.deleteById(userId);
    }
}
