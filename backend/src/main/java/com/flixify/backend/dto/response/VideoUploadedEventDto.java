package com.flixify.backend.dto.response;

import com.flixify.backend.model.Video;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;
import java.util.UUID;

@Data
@AllArgsConstructor
public class VideoUploadedEventDto {

    private final Path rawVideoFilePath;
    
    private final UUID uniqueId;
    
    private final String ruleName;
    
    private final Video video;
}
