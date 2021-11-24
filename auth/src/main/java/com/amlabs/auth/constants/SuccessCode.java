package com.amlabs.auth.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum SuccessCode {
    LOGIN_SUCCESS(OK, "로그인 성공"),
    TEST_SUCCESS(OK, "성공~");

    private final HttpStatus status;
    private final String msg;
}
