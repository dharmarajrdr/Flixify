package com.flixify.backend.service;

import java.util.ArrayList;
import java.util.List;

import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.dto.response.ChunkDto;
import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.ChunkRepository;

@Service
public class ChunkService {

    private final ChunkRepository chunkRepository;
    private final VideoService videoService;

    public ChunkService(ChunkRepository chunkRepository, VideoService videoService) {
        this.chunkRepository = chunkRepository;
        this.videoService = videoService;
    }

    public List<ChunkDto> getAllChunks(Integer userId, Integer videoId) throws UserNotFound, VideoNotExist, PermissionDenied {

        Video video = videoService.getVideo(userId, videoId);
        List<Chunk> chunks = chunkRepository.findByVideo(video);
        List<ChunkDto> chunkDtoList = new ArrayList<>();
        for(Chunk chunk: chunks) {
            chunkDtoList.add(new ChunkDto(chunk));
        }
        return chunkDtoList;
    }
}
