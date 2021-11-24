package com.amlabs.auth.exception;

import com.amlabs.auth.constants.CustomExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final CustomExceptionCode exceptionCode;
}
