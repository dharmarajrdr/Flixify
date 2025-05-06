package com.flixify.backend.controller;

import com.flixify.backend.dto.request.VideoUploadRequestDto;
import com.flixify.backend.service.VideoUploader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.security.Principal;

@RestController
@RequestMapping("/api/videos")
public class VideoUploadController {

    private final VideoUploader videoUploader;

    public VideoUploadController(VideoUploader videoUploader) {
        this.videoUploader = videoUploader;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(
            @RequestPart("file") MultipartFile file,
            @RequestPart("meta") VideoUploadRequestDto meta,
            Principal principal
    ) {
        String username = principal.getName(); // assuming JWT or session-based auth
        Path videoPath = videoUploader.upload(file, meta);
        return ResponseEntity.ok("Video uploaded successfully.");
    }

}