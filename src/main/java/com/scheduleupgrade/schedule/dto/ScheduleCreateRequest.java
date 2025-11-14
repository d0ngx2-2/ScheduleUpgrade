package com.scheduleupgrade.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleCreateRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 1, max = 10)
    private String title;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(min = 1, max = 50)
    private String content;
}
