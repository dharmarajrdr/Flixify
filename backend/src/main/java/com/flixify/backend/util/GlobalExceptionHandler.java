package com.flixify.backend.util;

import com.flixify.backend.custom_exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;

import java.io.NotSerializableException;

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

    @ExceptionHandler(VideoMissingInTrash.class)
    public ResponseEntity<ResponseDto> videoMissingInTrash(VideoMissingInTrash e) {

        return NOT_FOUND(e);
    }

    @ExceptionHandler(ChunkMissing.class)
    public ResponseEntity<ResponseDto> chunkMissing(ChunkMissing e) {

        return NOT_FOUND(e);
    }

    @ExceptionHandler(ChunkDoesNotExist.class)
    public ResponseEntity<ResponseDto> chunkDoesNotExist(ChunkDoesNotExist e) {

        return NOT_FOUND(e);
    }

    @ExceptionHandler(VideoAlreadyDeleted.class)
    public ResponseEntity<ResponseDto> videoAlreadyDeleted(VideoAlreadyDeleted e) {

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

    @ExceptionHandler(StreamDeletedVideoException.class)
    public ResponseEntity<ResponseDto> streamDeletedVideo(StreamDeletedVideoException e) {

        return NOT_FOUND(e);
    }

    @ExceptionHandler(PermissionDenied.class)
    public ResponseEntity<ResponseDto> permissionDenied(PermissionDenied e) {

        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseDto);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ResponseDto> nullPointer(NullPointerException e) {

        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }

    @ExceptionHandler(ChunkDoesNotSupportResolution.class)
    public ResponseEntity<ResponseDto> chunkDoesNotSupportResolution(ChunkDoesNotSupportResolution e) {

        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseDto> dataIntegrityViolationException(DataIntegrityViolationException e) {

        String message = DataIntegrityViolationExceptionParser.parse(e);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, message);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseDto);
    }

    @ExceptionHandler(NotSerializableException.class)
    public ResponseEntity<ResponseDto> notSerializable(NotSerializableException e) {

        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.FAILURE, e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
    }
}
