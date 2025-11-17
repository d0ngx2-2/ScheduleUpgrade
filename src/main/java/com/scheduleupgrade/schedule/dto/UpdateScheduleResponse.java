package com.scheduleupgrade.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleResponse {

    private final Long id;
    private final String title;
    private final String content;

    public UpdateScheduleResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
