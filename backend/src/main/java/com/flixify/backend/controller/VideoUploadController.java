package com.flixify.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flixify.backend.dto.request.VideoUploadRequestDto;
import com.flixify.backend.service.VideoUploader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.security.Principal;

@RestController
@RequestMapping("/v1/api/video")
public class VideoUploadController {

    private final VideoUploader videoUploader;

    public VideoUploadController(VideoUploader videoUploader) {
        this.videoUploader = videoUploader;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadVideo(
            @RequestPart("file") MultipartFile file,
            @RequestPart("meta") String metaJson,
            Principal principal
    ) throws Exception {
        String username = "dharma"; // assuming JWT or session-based auth
        ObjectMapper mapper = new ObjectMapper();
        VideoUploadRequestDto meta = mapper.readValue(metaJson, VideoUploadRequestDto.class);
        Path videoPath = videoUploader.upload(file, meta);
        return ResponseEntity.ok("Video uploaded successfully.");
    }

}