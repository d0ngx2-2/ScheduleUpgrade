package com.scheduleupgrade.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    private final Long id;
    private final String userName;
    private final String email;

    public SessionUser(Long id, String userName, String email, String password) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }
}
