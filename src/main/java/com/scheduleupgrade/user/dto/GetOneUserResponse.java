package com.scheduleupgrade.user.dto;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class GetOneUserResponse {
    private final Long id;
    private final String username;
    private final String email;
    @CreatedDate
    private final LocalDateTime createdDate;
    @LastModifiedDate
    private final LocalDateTime modifiedDate;

    public GetOneUserResponse(Long id, String username, String email, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
