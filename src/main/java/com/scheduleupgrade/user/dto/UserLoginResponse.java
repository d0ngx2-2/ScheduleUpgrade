package com.scheduleupgrade.user.dto;

import lombok.Getter;

@Getter
public class UserLoginResponse {

    private final Long id;
    private final String userName;
    private final String email;
    private final String message;

    public UserLoginResponse(Long id, String userName, String email, String message) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.message = message;
    }
}
