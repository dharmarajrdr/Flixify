package com.flixify.backend.util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VideoNotExist.class)
    public ResponseEntity<ResponseDto> videoNotExist(VideoNotExist e) {

        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, e.getMessage());

        return ResponseEntity.status(404).body(responseDto);
    }
}
