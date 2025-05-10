package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.request.VideoUploadRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface VideoUploaderService {

    public Path upload(MultipartFile videoFile, VideoUploadRequestDto videoUploadRequestDto);
}
