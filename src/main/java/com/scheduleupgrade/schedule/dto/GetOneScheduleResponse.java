package com.scheduleupgrade.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetOneScheduleResponse {

    private final long id;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdTime;
    private final LocalDateTime modifiedTime;

    public GetOneScheduleResponse(long id, String userName, String title, String content, LocalDateTime createdTime, LocalDateTime modifiedTime) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdTime = createdTime;
        this.modifiedTime = modifiedTime;
    }
}
