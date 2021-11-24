package com.amlabs.auth.exception;

import static org.springframework.http.HttpStatus.*;
import com.amlabs.auth.dto.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<BaseResponse> CustomHandlerException(CustomException e){
        return BaseResponse.BaseErrorResponse(e.getExceptionCode());
    }

    // 404
    @ExceptionHandler(value = {NoHandlerFoundException.class })
    protected ResponseEntity<BaseResponse> NotFoundHandlerException(NoHandlerFoundException e, HttpServletRequest request){
        log.warn("[404 Error] :" + request.getMethod() + " " + request.getRequestURI());
        return BaseResponse.BasicErrorResponse(NOT_FOUND, request.getMethod() + " " + request.getRequestURI() + "요청을 찾을 수 없습니다.");
    }

    // Rest Exception Handler
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<BaseResponse> HandlerException(Exception e, HttpServletRequest request){
        log.error("[500 Error] : " + request.getMethod() + " " + request.getRequestURI());
        return BaseResponse.BasicErrorResponse(INTERNAL_SERVER_ERROR, request.getRequestURI() + "서버 내에서 요청 처리중 에러가 발생했습니다.");
    }

}
