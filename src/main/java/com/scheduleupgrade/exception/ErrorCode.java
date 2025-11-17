package com.scheduleupgrade.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    USER_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    USER_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 유저는 권한이 없습니다."),
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 올바르지 않습니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "없는 일정입니다."),
    SCHEDULE_FORBIDDEN(HttpStatus.FORBIDDEN, "해당 일정에 대한 권한이 없습니다."),
    INVALID_EMAIL_INPUT(HttpStatus.BAD_REQUEST, "올바르지 않은 이메일입니다.");
//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생했습니다.");

    private HttpStatus status;
    private String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
