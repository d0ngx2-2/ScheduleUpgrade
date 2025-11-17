package com.scheduleupgrade.schedule.dto;

import lombok.Getter;
import java.time.LocalDateTime;


@Getter
public class GetAllScheduleResponse {

    private final String userName;
    private final String title;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;


    public GetAllScheduleResponse(String userName, String title, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.userName = userName;
        this.title = title;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}
