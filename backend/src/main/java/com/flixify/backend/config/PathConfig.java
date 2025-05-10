package com.flixify.backend.config;

import com.flixify.backend.util.LocalDisk;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PathConfig {

    @Value("${video.storage.directory}")
    private String videoStorageDirectory;

    @Value("${chunk.storage.directory}")
    private String chunkStorageDirectory;

    @Value("${manifest.storage.directory}")
    private String manifestStoragePath;

    public static String VIDEO_STORAGE_DIRECTORY;
    public static String CHUNK_STORAGE_DIRECTORY;
    public static String MANIFEST_STORAGE_PATH;

    @PostConstruct
    public void init() {
        VIDEO_STORAGE_DIRECTORY = videoStorageDirectory;
        CHUNK_STORAGE_DIRECTORY = chunkStorageDirectory;
        MANIFEST_STORAGE_PATH = manifestStoragePath;

        LocalDisk.createDirectoryIfNotExists(new File(VIDEO_STORAGE_DIRECTORY));
        LocalDisk.createDirectoryIfNotExists(new File(CHUNK_STORAGE_DIRECTORY));
        // LocalDisk.createDirectoryIfNotExists(new File(MANIFEST_STORAGE_PATH));
    }
}
