package com.amlabs.auth.constants;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CustomExceptionCode {

    /* 400 - Bad Request*/
    PAYLOAD_BAD_REQUEST(BAD_REQUEST, "올바르지 않는 데이터 형식입니다."),

    /* 401 - 유효하지 않음 */
    UNAUTHORIZED_ACCESS_TOKEN(UNAUTHORIZED, "유효하지 않는 access token입니다.."),

    /* 404 */
    EMAIL_NOT_FOUND(NOT_FOUND, "존재하지 않는 이메일입니다.");


    private final HttpStatus status;
    private final String msg;
}
