package com.flixify.backend.strategy.VideoSplitter;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.UnableToSplitException;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.interfaces.VideoSplitterRuleService;
import com.flixify.backend.service.interfaces.VideoSplitterService;
import com.flixify.backend.util.LocalDisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

@Service
public class SizeBasedVideoSplitter implements VideoSplitterService {

    @Autowired
    private VideoSplitterRuleService videoSplitterRuleService;

    private final static Integer MAXIMUM_CHUNK_SIZE = 5 * 1024 * 8; // 5MB

    @Override
    public VideoSplitterRule getVideoSplitterRule() {

        return videoSplitterRuleService.getVideoSplitterRule("SIZE_BASED_VIDEO_SPLITTER");
    }

    private long getBitrate(String videoFilePath) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(
                "ffprobe", "-v", "error", "-select_streams",
                "v:0", "-show_entries", "stream=bit_rate", "-of",
                "default=noprint_wrappers=1:nokey=1", videoFilePath
        );

        Process process = builder.start();
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append(System.lineSeparator());
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("ffprobe failed with exit code: " + exitCode);
        }

        String result = output.toString().trim(); // remove trailing newline
        if (result.isEmpty()) {
            throw new RuntimeException("Bitrate info is empty. ffprobe output:\n" + output);
        }

        long bitrateInBps = Long.parseLong(result); // bitrate in bits per second
        return bitrateInBps / 1000;
    }

    private void start(String videoFilePath, String chunkFilePathPattern) throws IOException, InterruptedException {

        long videoBitrateKbps = getBitrate(videoFilePath);  // in KB
        long estimatedDuration = MAXIMUM_CHUNK_SIZE / videoBitrateKbps;

        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", videoFilePath,
                "-c", "copy", "-f", "segment",
                "-segment_time", String.valueOf(estimatedDuration),
                "-reset_timestamps", "1",
                chunkFilePathPattern // e.g., chunk_%03d.ts
        );

        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new UnableToSplitException("FFmpeg failed with exit code: " + exitCode);
        }
    }

    @Override
    public File splitVideo(Video video, Path videoFilePath, Resolution resolution) throws IOException, InterruptedException {

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

