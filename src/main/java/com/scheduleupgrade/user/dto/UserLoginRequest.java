package com.scheduleupgrade.user.dto;

import lombok.Getter;

@Getter
public class UserLoginRequest {
    private String email;
    private String password;
}
