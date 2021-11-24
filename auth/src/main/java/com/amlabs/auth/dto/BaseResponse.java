package com.amlabs.auth.dto;


import com.amlabs.auth.constants.CustomExceptionCode;
import com.amlabs.auth.constants.SuccessCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BaseResponse {

    private Boolean success;
    private String msg;
    private LocalDateTime timestamp = LocalDateTime.now();

    @Builder
    public BaseResponse(Boolean success, String msg){
        this.success = success;
        this.msg = msg;
    }

    public static ResponseEntity<BaseResponse> BaseErrorResponse(CustomExceptionCode exceptionCode){
        return ResponseEntity
                .status(exceptionCode.getStatus())
                .body(
                    BaseResponse.builder()
                            .success(false)
                            .msg(exceptionCode.getMsg())
                            .build()
                );
    }

    public static ResponseEntity<BaseResponse> BasicErrorResponse(HttpStatus status, String msg){
        return ResponseEntity
                .status(status)
                .body(
                        BaseResponse.builder()
                                .success(false)
                                .msg(msg)
                                .build()
                );
    }

    public static ResponseEntity<BaseResponse> BaseSuccessResponse(SuccessCode successCode){
        return ResponseEntity
                .status(successCode.getStatus())
                .body(
                        BaseResponse.builder()
                                .success(true)
                                .msg(successCode.getMsg())
                                .build()
                );

    }
}
