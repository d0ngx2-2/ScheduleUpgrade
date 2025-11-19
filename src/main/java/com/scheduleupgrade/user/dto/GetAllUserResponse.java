package com.scheduleupgrade.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetAllUserResponse {

    private final Long id;
    private final String userName;
    private final String email;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    public GetAllUserResponse(Long id, String userName, String email, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
