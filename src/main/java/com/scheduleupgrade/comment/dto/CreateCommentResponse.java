package com.scheduleupgrade.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentResponse {

    private final Long id;
    private final String userName;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    public CreateCommentResponse(Long id, String userName, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userName = userName;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
