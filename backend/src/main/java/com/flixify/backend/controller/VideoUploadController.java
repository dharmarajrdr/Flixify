package com.flixify.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flixify.backend.dto.request.VideoUploadRequestDto;
import com.flixify.backend.dto.response.UploadVideoResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import com.flixify.backend.service.interfaces.VideoUploaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/v1/api/video")
public class VideoUploadController {

    private final VideoUploaderService videoUploaderService;

    public VideoUploadController(VideoUploaderService videoUploaderService) {
        this.videoUploaderService = videoUploaderService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadVideoResponseDto> uploadVideo(
            @RequestPart("file") MultipartFile file,
            @RequestPart("meta") String metaJson,
            Principal principal
    ) throws Exception {
        String username = "dharma"; // assuming JWT or session-based auth
        ObjectMapper mapper = new ObjectMapper();
        VideoUploadRequestDto meta = mapper.readValue(metaJson, VideoUploadRequestDto.class);
        videoUploaderService.upload(file, meta);
        UploadVideoResponseDto responseDto = new UploadVideoResponseDto();
        responseDto.setStatus(ResponseStatusEnum.SUCCESS);
        responseDto.setMessage("Video uploaded successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}