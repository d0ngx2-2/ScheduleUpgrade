package com.scheduleupgrade.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 100)
    private String content;
}
