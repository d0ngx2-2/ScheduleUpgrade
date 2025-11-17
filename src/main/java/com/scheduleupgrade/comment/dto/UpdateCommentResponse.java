package com.scheduleupgrade.comment.dto;

import lombok.Getter;

@Getter
public class UpdateCommentResponse {

    private final Long id;
    private final String content;

    public UpdateCommentResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
