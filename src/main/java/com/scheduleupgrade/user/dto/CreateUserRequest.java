package com.scheduleupgrade.user.dto;

import lombok.Getter;

@Getter
public class CreateUserRequest {
    private String userName;
    private String email;
    private String password;
}
