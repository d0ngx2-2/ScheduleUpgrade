package com.scheduleupgrade.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class CreateCommentRequest {

    @NotBlank(message = "내용을 작성해주십시오.")
    @Size(max = 100)
    private String content;
}
