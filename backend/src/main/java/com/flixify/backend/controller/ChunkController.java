package com.flixify.backend.controller;

import com.flixify.backend.dto.response.ChunkDto;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.service.interfaces.ChunkService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api")
public class ChunkController {

    private final ChunkService chunkService;

    public ChunkController(ChunkService chunkService) {
        this.chunkService = chunkService;
    }

    @GetMapping("/video/{fileId}/chunks")
    public ResponseEntity<ResponseDto> getAllChunks(@RequestParam Integer userId, @PathVariable UUID fileId) {

        List<ChunkDto> chunks = chunkService.getAllChunks(userId, fileId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the chunks of the given video.", chunks, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/video/{fileId}/chunk/{chunkId}")
    public ResponseEntity<Resource> getChunk(@RequestParam Integer userId, @PathVariable UUID fileId, @PathVariable Integer chunkId) throws MalformedURLException {

        Resource resource = chunkService.getChunkFile(fileId, chunkId, userId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaTypeFactory
                        .getMediaType(resource)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resource);
    }
}
