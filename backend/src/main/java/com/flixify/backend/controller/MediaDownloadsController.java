package com.flixify.backend.controller;

import com.flixify.backend.dto.response.ChunkDownloadDto;
import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.service.interfaces.MediaDownloadsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/api/media")
public class MediaDownloadsController {

    private final MediaDownloadsService mediaDownloadsService;

    @GetMapping("/stats/{fileId}")
    public ResponseEntity<ResponseDto> getChunkDownloads(@PathVariable UUID fileId, @RequestParam Integer userId) {

        List<ChunkDownloadDto> chunkDownloadDtos = mediaDownloadsService.getChunkDownloads(fileId, userId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "Fetched the chunk downloads stats.", chunkDownloadDtos, null);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
