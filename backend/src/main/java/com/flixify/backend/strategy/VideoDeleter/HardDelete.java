package com.flixify.backend.strategy.VideoDeleter;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.VideoDeletedPermanently;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.VideoRepository;
import com.flixify.backend.service.interfaces.VideoDeleterService;
import com.flixify.backend.util.LocalDisk;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@AllArgsConstructor
/**
 * Deletes the video meta and media permanently.
 */
public class HardDelete implements VideoDeleterService {

    private final VideoRepository videoRepository;

    @Override
    public void delete(Video video) {

        videoRepository.delete(video);

        File rawVideoFile = new File(PathConfig.VIDEO_STORAGE_DIRECTORY + "/" + video.getFileId().toString() + ".mp4");
        File chunkFiles = new File(PathConfig.CHUNK_STORAGE_DIRECTORY + "/" + video.getFileId().toString());
        LocalDisk.deleteFile(rawVideoFile.toPath());
        LocalDisk.deleteDirectory(chunkFiles.toPath());
    }

    @Override
    public void recover(Video video) {

        throw new VideoDeletedPermanently(video);
    }
}
