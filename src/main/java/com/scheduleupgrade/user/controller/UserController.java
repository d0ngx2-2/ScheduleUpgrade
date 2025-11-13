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
    public ResponseEntity<Void> logout(@SessionAttribute(name = "loginUser", required = false)SessionUser sessionUser, HttpSession session) {
        if(sessionUser != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
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
    public ResponseEntity<UpdateUserResponse> updateUser(@SessionAttribute SessionUser sessionUser, @PathVariable Long userId, @RequestBody UpdateUserRequest request) {
        if(sessionUser != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }else if(!sessionUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(userId, request));
    }

    //삭제 기능
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@SessionAttribute SessionUser sessionUser, @PathVariable Long userId) {
        if(sessionUser != null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if(!sessionUser.getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
