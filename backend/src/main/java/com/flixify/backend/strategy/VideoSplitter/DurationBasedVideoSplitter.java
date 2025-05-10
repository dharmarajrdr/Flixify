package com.flixify.backend.strategy.VideoSplitter;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.UnableToSplitException;
import com.flixify.backend.custom_exceptions.VideoMissing;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.interfaces.VideoSplitterRuleService;
import com.flixify.backend.service.interfaces.VideoSplitterService;
import com.flixify.backend.util.LocalDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class DurationBasedVideoSplitter implements VideoSplitterService {

    @Autowired
    private VideoSplitterRuleService videoSplitterRuleService;

    private final static Integer CHUNK_DURATION_IN_SECONDS = 10;

    @Override
    public VideoSplitterRule getVideoSplitterRule() {

        return videoSplitterRuleService.getVideoSplitterRule("DURATION_BASED_VIDEO_SPLITTER");
    }

    private void checkVideoFileExistence(Path videoFile) {
        if (!Files.exists(videoFile)) {
            throw new VideoMissing(videoFile.toAbsolutePath().toString());
        }
    }

    private void start(String videoFilePath, String chunkFilePathPattern) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", videoFilePath,
                "-c", "copy", "-f", "segment",
                "-segment_format", "mp4",
                "-segment_time", CHUNK_DURATION_IN_SECONDS.toString(),
                "-reset_timestamps", "1",
                chunkFilePathPattern
        );

        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new UnableToSplitException("FFmpeg failed with exit code: " + exitCode);
        }
    }

    @Override
    public File splitVideo(Video video, Path videoFilePath, Resolution resolution) throws IOException, InterruptedException {

        checkVideoFileExistence(videoFilePath);

        File file = videoFilePath.toFile();
        String name = file.getName();
        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        File chunksDirectory = new File(PathConfig.CHUNK_STORAGE_DIRECTORY, nameWithoutExtension + "/" + resolution.getTitle());

        LocalDisk.createDirectoryIfNotExists(chunksDirectory);

        String chunkFilePathPattern = new File(chunksDirectory, "%d.mp4").getAbsolutePath();

        start(videoFilePath.toAbsolutePath().toString(), chunkFilePathPattern);

        return chunksDirectory;
    }
}
