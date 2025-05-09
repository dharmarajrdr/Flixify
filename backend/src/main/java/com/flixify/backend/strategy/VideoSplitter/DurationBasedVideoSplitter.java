package com.flixify.backend.strategy.VideoSplitter;

import com.flixify.backend.custom_exceptions.UnableToSplitException;
import com.flixify.backend.custom_exceptions.VideoMissing;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.VideoSplitterRuleService;
import com.flixify.backend.service.VideoSplitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

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

    private void createDirectoryIfNotExists(File directory) {

        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }
    }

    private double getVideoDuration(File file) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(
                "ffprobe", "-v", "error", "-show_entries",
                "format=duration", "-of", "default=noprint_wrappers=1:nokey=1",
                file.getAbsolutePath()
        );

        Process process = builder.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );
        String line = reader.readLine();

        int exitCode = process.waitFor();
        if (exitCode != 0 || line == null) {

            throw new RuntimeException("Failed to get duration for: " + file.getName());
        }

        return Double.parseDouble(line.trim());
    }

    private List<Chunk> getChunksMetaData(File chunksDirectory, Video video) throws IOException, InterruptedException {

        File[] chunkFiles = chunksDirectory.listFiles((dir, fileName) -> fileName.endsWith(".mp4"));
        if (chunkFiles == null) {
            return List.of();
        }

        List<Chunk> chunks = new java.util.ArrayList<>();
        double currentStart = 0.0;

        // Sort chunk files by numeric name (1.mp4, 2.mp4, ...)
        Arrays.sort(chunkFiles, (f1, f2) -> {
            int n1 = Integer.parseInt(f1.getName().replace(".mp4", ""));
            int n2 = Integer.parseInt(f2.getName().replace(".mp4", ""));
            return Integer.compare(n1, n2);
        });

        int chunkId = 1;
        for (File file : chunkFiles) {
            double duration = getVideoDuration(file);
            double endTime = currentStart + duration;
            double size = file.length(); // in bytes
            Chunk chunk = new Chunk();
            chunk.setChunkId(chunkId++);
            chunk.setStartTime(Math.floor(currentStart));
            chunk.setEndTime(Math.floor(endTime));
            chunk.setSize(size);
            chunk.setVideo(video);
            chunks.add(chunk);
            currentStart = endTime;
        }

        return chunks;
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
    public List<Chunk> splitVideo(Video video, Path videoFilePath) throws IOException, InterruptedException {

        checkVideoFileExistence(videoFilePath);

        File file = videoFilePath.toFile();
        String name = file.getName();
        String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
        File chunksDirectory = new File(file.getParent(), "chunks/"+ nameWithoutExtension);

        createDirectoryIfNotExists(chunksDirectory);

        String chunkFilePathPattern = new File(chunksDirectory, "%d.mp4").getAbsolutePath();

        start(videoFilePath.toAbsolutePath().toString(), chunkFilePathPattern);

        List<Chunk> chunks = getChunksMetaData(chunksDirectory, video);

        return chunks;
    }
}
