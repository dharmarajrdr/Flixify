package com.flixify.backend.controller;

import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.dto.response.ChunkDto;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.service.ChunkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChunkController {

    private final ChunkService chunkService;

    public ChunkController(ChunkService chunkService) {
        this.chunkService = chunkService;
    }

    @GetMapping("/videos/{videoId}/chunks")
    public ResponseEntity<ResponseDto> getAllChunks(@RequestParam Integer userId, @PathVariable Integer videoId) throws UserNotFound, VideoNotExist, PermissionDenied {

        List<ChunkDto> chunks = chunkService.getAllChunks(userId, videoId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the chunks of the given video.", chunks, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
