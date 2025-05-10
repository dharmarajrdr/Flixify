package com.flixify.backend.service.implementations;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.flixify.backend.custom_exceptions.ChunkDoesNotExist;
import com.flixify.backend.custom_exceptions.ChunkDoesNotSupportResolution;
import com.flixify.backend.dto.response.ChunkDto;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.service.interfaces.ChunkService;
import com.flixify.backend.service.interfaces.VideoService;
import org.springframework.stereotype.Service;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.ChunkRepository;

@Service
public class ChunkServiceImpl implements ChunkService {

    private final ChunkRepository chunkRepository;
    private final VideoService videoService;

    public ChunkServiceImpl(ChunkRepository chunkRepository, VideoService videoService) {
        this.chunkRepository = chunkRepository;
        this.videoService = videoService;
    }

    @Override
    public List<ChunkDto> getAllChunks(Integer userId, UUID fileId) {

        Video video = videoService.getVideo(userId, fileId);
        List<Chunk> chunks = chunkRepository.findByVideo(video);
        List<ChunkDto> chunkDtoList = new ArrayList<>();
        for (Chunk chunk : chunks) {
            chunkDtoList.add(new ChunkDto(chunk));
        }
        return chunkDtoList;
    }

    @Override
    public List<ChunkDto> getAllChunksByResolution(Integer userId, UUID fileId, Resolution resolution) {

        Video video = videoService.getVideo(userId, fileId);
        List<Chunk> chunks = chunkRepository.findByVideoAndResolution(video, resolution);
        List<ChunkDto> chunkDtoList = new ArrayList<>();
        for (Chunk chunk : chunks) {
            chunkDtoList.add(new ChunkDto(chunk));
        }
        return chunkDtoList;
    }

    public List<Chunk> saveAll(List<Chunk> chunks) {

        return chunkRepository.saveAll(chunks);
    }

    @Override
    public void checkChunkWithResolutionExists(Video video, Integer chunkId, Resolution resolution) {

        List<Chunk> chunks = chunkRepository.findByVideoAndChunkId(video, chunkId);
        if (chunks.isEmpty()) {
            throw new ChunkDoesNotExist(chunkId, video.getFileId());
        }

        boolean resolutionExists = false;
        for (Chunk chunk : chunks) {
            if (chunk.getResolution().equals(resolution)) {
                resolutionExists = true;
                break;
            }
        }
        if (!resolutionExists) {
            throw new ChunkDoesNotSupportResolution(chunkId, resolution);
        }
    }

    @Override
    public Optional<Chunk> getChunkByVideoAndResolutionAndChunkId(Video video, Resolution resolution, Integer chunkId) {

        return chunkRepository.findChunkByVideoAndResolutionAndChunkId(video, resolution, chunkId);
    }

    @Override
    public List<Chunk> getChunksMetaData(File chunksDirectory, Video video, Resolution resolution) throws IOException, InterruptedException {

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
            double duration = videoService.getVideoDuration(file);
            double endTime = currentStart + duration;
            double size = file.length(); // in bytes
            Chunk chunk = Chunk.builder()
                    .chunkId(chunkId++)
                    .startTime(Math.floor(currentStart))
                    .endTime(Math.floor(endTime))
                    .size(size)
                    .video(video)
                    .resolution(resolution)
                    .build();
            chunks.add(chunk);
            currentStart = endTime;
        }

        return chunks;
    }
}
