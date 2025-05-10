package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.response.ChunkDto;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChunkService {

    public List<Chunk> getChunksMetaData(File chunksDirectory, Video video, Resolution resolution) throws IOException, InterruptedException;

    public List<Chunk> saveAll(List<Chunk> splittedChunks);

    public List<ChunkDto> getAllChunks(Integer userId, UUID fileId);

    public List<ChunkDto> getAllChunksByResolution(Integer userId, UUID fileId, Resolution resolution);

    public void checkChunkWithResolutionExists(Video video, Integer chunkId, Resolution resolution);

    public Optional<Chunk> getChunkByVideoAndResolutionAndChunkId(Video video, Resolution resolution, Integer chunkId);
}
