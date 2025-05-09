package com.flixify.backend.service;

import com.flixify.backend.dto.request.VideoUploadRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface VideoUploader {

    public Path upload(MultipartFile videoFile, VideoUploadRequestDto videoUploadRequestDto);
}
