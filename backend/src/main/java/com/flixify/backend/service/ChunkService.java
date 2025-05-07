package com.flixify.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.flixify.backend.custom_exceptions.InvalidChunkStatus;
import com.flixify.backend.dto.request.AddChunkDto;
import com.flixify.backend.dto.response.ChunkDto;
import com.flixify.backend.model.ChunkStatus;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.repository.ChunkStatusRepository;
import org.springframework.stereotype.Service;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.ChunkRepository;

@Service
public class ChunkService {

    private final ChunkRepository chunkRepository;
    private final ChunkStatusRepository chunkStatusRepository;
    private final VideoService videoService;
    private final ResolutionService resolutionService;

    public ChunkService(ChunkRepository chunkRepository, VideoService videoService, ResolutionService resolutionService, ChunkStatusRepository chunkStatusRepository) {
        this.chunkRepository = chunkRepository;
        this.videoService = videoService;
        this.resolutionService = resolutionService;
        this.chunkStatusRepository = chunkStatusRepository;
    }

    public List<ChunkDto> getAllChunks(Integer userId, Integer videoId) {

        Video video = videoService.getVideo(userId, videoId);
        List<Chunk> chunks = chunkRepository.findByVideo(video);
        List<ChunkDto> chunkDtoList = new ArrayList<>();
        for (Chunk chunk : chunks) {
            chunkDtoList.add(new ChunkDto(chunk));
        }
        return chunkDtoList;
    }

    private Chunk constructChunk(AddChunkDto addChunkDto) {

        Integer pixel = addChunkDto.getPixel();
        Resolution resolution = resolutionService.getResolution(pixel);
        ChunkStatus chunkStatus = getChunkStatus(addChunkDto.getChunkStatus());

        Chunk chunk = addChunkDto.toChunk();
        chunk.setResolution(resolution);
        chunk.setChunkStatus(chunkStatus);
        return chunk;
    }

    public ChunkStatus getChunkStatus(String status) {

        return chunkStatusRepository.findByStatus(status).orElseThrow(() -> new InvalidChunkStatus(status));
    }

    public List<ChunkDto> addChunks(Integer userId, Integer videoId, List<AddChunkDto> addChunkDtoList) {

        Video video = videoService.getVideo(userId, videoId);
        int chunksCount = addChunkDtoList.size();
        List<ChunkDto> chunks = new ArrayList<>();
        for (int chunkId = 0; chunkId < chunksCount; chunkId++) {
            AddChunkDto addChunkDto = addChunkDtoList.get(chunkId);
            Chunk chunk = constructChunk(addChunkDto);
            chunk.setVideo(video);
            chunk.setChunkId(chunkId + 1);
            chunks.add(new ChunkDto(chunk));
            chunkRepository.save(chunk);
        }
        return chunks;
    }
}
