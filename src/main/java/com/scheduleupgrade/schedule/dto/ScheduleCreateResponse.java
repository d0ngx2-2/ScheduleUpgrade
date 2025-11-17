package com.scheduleupgrade.schedule.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ScheduleCreateResponse {

    private final Long id;
    private final String userName;
    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;

    public ScheduleCreateResponse(Long id, String userName, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.userName = userName;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
