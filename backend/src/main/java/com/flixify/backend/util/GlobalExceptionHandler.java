package com.flixify.backend.util;

import com.flixify.backend.custom_exceptions.AccountNotFound;
import com.flixify.backend.custom_exceptions.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private ResponseEntity NOT_FOUND(Exception e) {

        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(VideoNotExist.class)
    public ResponseEntity<ResponseDto> videoNotExist(VideoNotExist e) {

        return NOT_FOUND(e);
    }

    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<ResponseDto> accountNotFound(AccountNotFound e) {

        return NOT_FOUND(e);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ResponseDto> userNotFound(UserNotFound e) {

        return NOT_FOUND(e);
    }
}
