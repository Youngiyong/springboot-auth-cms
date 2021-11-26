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
    EMAIL_IS_EXIST(BAD_REQUEST, "존재하는 이메일입니다."),
    USERNAME_IS_EXIST(BAD_REQUEST, "존재하는 유저입니다."),
    REQUEST_ROLE_NOT_FOUND(BAD_REQUEST, "payload role is not found"),
    EXPIRE_REFRESH_TOKEN(BAD_REQUEST, "만료된 토큰입니다"),

    /* 401 - 유효하지 않음 */
    UNAUTHORIZED_ACCESS_TOKEN(UNAUTHORIZED, "유효하지 않는 엑세스 토큰입니다."),

    /* 404 */
    EMAIL_NOT_FOUND(NOT_FOUND, "존재하지 않는 이메일입니다."),
    USERNAME_NOT_FOUND(NOT_FOUND, "존재하지 않는 아이디입니다.");

    private final HttpStatus status;
    private final String msg;
}
