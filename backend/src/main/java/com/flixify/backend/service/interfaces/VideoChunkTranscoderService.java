package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.request.VideoUploadRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public interface VideoChunkTranscoderService {

    public void splitUploadedVideIntoDifferentResolutions(MultipartFile videoFile, Path filePath, VideoUploadRequestDto videoUploadRequestDto, UUID uniqueId) throws IOException, InterruptedException;
}
