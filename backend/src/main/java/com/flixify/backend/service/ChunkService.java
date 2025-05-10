package com.flixify.backend.service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.ChunkMissing;
import com.flixify.backend.custom_exceptions.InvalidChunkStatus;
import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.dto.request.AddChunkDto;
import com.flixify.backend.dto.response.ChunkDto;
import com.flixify.backend.model.ChunkStatus;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.repository.ChunkStatusRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

    public List<ChunkDto> getAllChunks(Integer userId, UUID fileId) {

        Video video = videoService.getVideo(userId, fileId);
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

        Chunk chunk = addChunkDto.toChunk();
        chunk.setResolution(resolution);
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

    public List<Chunk> saveAll(List<Chunk> chunks) {

        return chunkRepository.saveAll(chunks);
    }

    private Resource getChunkAsResource(UUID fileId, Integer chunkId) throws MalformedURLException {

        File file = new File(PathConfig.CHUNK_STORAGE_DIRECTORY + "/" + fileId + "/" + chunkId + ".mp4");
        if(!file.exists()) {
            throw new ChunkMissing(fileId, chunkId);
        }

        Path path = file.toPath();
        return new UrlResource(path.toUri());
    }

    public Resource getChunkFile(UUID fileId, Integer chunkId, Integer userId) throws MalformedURLException {

        if(videoService.isOwner(userId, fileId)) {
            return getChunkAsResource(fileId, chunkId);
        }
        throw new PermissionDenied("Unable to fetch the video of another user.");
    }
}
