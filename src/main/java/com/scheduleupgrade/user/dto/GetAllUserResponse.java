package com.scheduleupgrade.user.dto;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class GetAllUserResponse {
    private final Long id;
    private final String userName;
    private final String email;
    @CreatedDate
    private final LocalDateTime createdDate;
    @LastModifiedDate
    private final LocalDateTime modifiedDate;

    public GetAllUserResponse(Long id, String userName, String email, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
