package com.flixify.backend.util;

import com.flixify.backend.custom_exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;

@RestControllerAdvice
public class GlobalExceptionHandler {


    private ResponseEntity<ResponseDto> NOT_FOUND(RuntimeException e) {

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

    @ExceptionHandler(ResolutionNotFound.class)
    public ResponseEntity<ResponseDto> resolutionNotFound(ResolutionNotFound e) {

        return NOT_FOUND(e);
    }

    @ExceptionHandler(PermissionDenied.class)
    public ResponseEntity<ResponseDto> permissionDenied(PermissionDenied e) {

        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
    }
}
