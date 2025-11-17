package com.scheduleupgrade.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {

    @NotBlank(message = "유저이름을 입력해주세요.")
    @Size(min = 1, max = 5)
    private String userName;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
