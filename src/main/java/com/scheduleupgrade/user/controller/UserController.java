package com.scheduleupgrade.user.controller;

import com.scheduleupgrade.user.dto.*;
import com.scheduleupgrade.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원가입 기능
    @PostMapping("/users/signup")
    public ResponseEntity<CreateUserResponse> saveUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request));
    }

    //로그인 기능
    @PostMapping("/users/login")
    public ResponseEntity<Void> login(@RequestBody UserLoginRequest request, HttpSession session) {
        userService.login(request,session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //로그아웃 기능
    @PostMapping("/users/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        userService.logout(session);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //전체 조회 기능
    @GetMapping("/users")
    public ResponseEntity<List<GetAllUserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }

    //선택 조회 기능
    @GetMapping("/users/{userId}")
    public ResponseEntity<GetOneUserResponse> getOneUsers(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getOne(userId));
    }

    //수정 기능
    @PutMapping("/users/{userId}")
    public ResponseEntity<UpdateUserResponse> updateUser(@PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    //삭제 기능
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
