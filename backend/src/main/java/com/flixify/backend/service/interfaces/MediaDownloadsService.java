package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.response.ChunkDownloadDto;
import org.springframework.core.io.Resource;

import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

public interface MediaDownloadsService {

    public Resource getChunkFile(UUID fileId, Integer chunkId, Integer userId, String resolutionTitle) throws MalformedURLException;

    public List<ChunkDownloadDto> getChunkDownloads(UUID fileId, Integer userId);
}
