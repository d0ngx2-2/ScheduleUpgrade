package com.scheduleupgrade.schedule.dto;

import lombok.Getter;

@Getter
public class ScheduleCreateRequest {
    private String userName;
    private String title;
    private String content;
}
