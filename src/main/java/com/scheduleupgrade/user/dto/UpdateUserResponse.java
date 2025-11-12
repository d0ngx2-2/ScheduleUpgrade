package com.scheduleupgrade.user.dto;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class UpdateUserResponse {
    private final Long id;
    private final String userName;
    private final String email;
    @LastModifiedDate
    private final LocalDateTime modifiedDate;

    public UpdateUserResponse(Long id, String userName, String email, LocalDateTime modifiedDate) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.modifiedDate = modifiedDate;
    }
}
