package com.flixify.backend.service;

import java.util.List;

import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.custom_exceptions.UserNotFound;
import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.ChunkRepository;

@Service
public class ChunkService {

    private ChunkRepository chunkRepository;
    private VideoService videoService;

    public ChunkService(ChunkRepository chunkRepository, VideoService videoService) {
        this.chunkRepository = chunkRepository;
        this.videoService = videoService;
    }

    public List<Chunk> getAllChunks(Integer userId, Integer videoId) throws UserNotFound, VideoNotExist, PermissionDenied {

        Video video = videoService.getVideo(userId, videoId);
        return chunkRepository.findByVideo(video);
    }
}
