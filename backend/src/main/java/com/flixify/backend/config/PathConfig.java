package com.flixify.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PathConfig {

    @Value("${video.storage.directory}")
    private String videoStorageDirectory;

    @Value("${chunk.storage.directory}")
    private String chunkStorageDirectory;

    public static String VIDEO_STORAGE_DIRECTORY;
    public static String CHUNK_STORAGE_DIRECTORY;

    @PostConstruct
    public void init() {
        VIDEO_STORAGE_DIRECTORY = videoStorageDirectory;
        CHUNK_STORAGE_DIRECTORY = chunkStorageDirectory;
    }
}
