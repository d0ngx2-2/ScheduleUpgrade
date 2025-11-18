package com.scheduleupgrade.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetPageScheduleResponse {

    private final String userName;
    private final String title;
    private final String content;
    private final int commentCount;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    public GetPageScheduleResponse(String userName, String title, String content, int commentCount, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
