package com.flixify.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.ChunkRepository;
import com.flixify.backend.repository.VideoRepository;

@Service
public class ChunkService {

    private ChunkRepository chunkRepository;
    private VideoRepository videoRepository;

    public ChunkService(ChunkRepository chunkRepository, VideoRepository videoRepository) {
        this.chunkRepository = chunkRepository;
        this.videoRepository = videoRepository;
    }

    public List<Chunk> getAllChunks(Integer videoId) throws VideoNotExist {

        Video video = videoRepository.findById(videoId).orElseThrow(() -> new VideoNotExist(videoId));

        return chunkRepository.findByVideo(video);
    }
}
