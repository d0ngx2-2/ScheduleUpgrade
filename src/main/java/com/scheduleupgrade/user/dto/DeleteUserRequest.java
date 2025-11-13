package com.scheduleupgrade.user.dto;

import lombok.Getter;

@Getter
public class DeleteUserRequest {
    private Long userId;
    private String password;
}
