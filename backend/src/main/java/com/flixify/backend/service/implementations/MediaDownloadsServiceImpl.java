package com.flixify.backend.service.implementations;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.ChunkMissing;
import com.flixify.backend.custom_exceptions.StreamDeletedVideoException;
import com.flixify.backend.dto.response.ChunkDownloadDto;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.MediaDownloads;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.MediaDownloadsRepository;
import com.flixify.backend.service.interfaces.ChunkService;
import com.flixify.backend.service.interfaces.MediaDownloadsService;
import com.flixify.backend.service.interfaces.ResolutionService;
import com.flixify.backend.service.interfaces.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;

@Service
@AllArgsConstructor
public class MediaDownloadsServiceImpl implements MediaDownloadsService {

    private final VideoService videoService;
    private final ChunkService chunkService;
    private final ResolutionService resolutionService;
    private final MediaDownloadsRepository mediaDownloadsRepository;

    private Resource getChunkAsResource(UUID fileId, Integer chunkId, Resolution resolution) throws MalformedURLException {

        File file = new File(PathConfig.CHUNK_STORAGE_DIRECTORY + "/" + fileId + "/" + resolution.getTitle() + "/" + chunkId + ".mp4");
        if (!file.exists()) {
            throw new ChunkMissing(fileId, chunkId);
        }

        Path path = file.toPath();
        return new UrlResource(path.toUri());
    }

    private void incrementDownloads(Video video, Chunk chunk, Resolution resolution) {

        MediaDownloads mediaDownloads = mediaDownloadsRepository.findByVideoAndChunk(video, chunk).orElse(new MediaDownloads(video, chunk, resolution));
        mediaDownloads.setDownloads(mediaDownloads.getDownloads() + 1);
        mediaDownloadsRepository.save(mediaDownloads);
    }

    @Override
    public Resource getChunkFile(UUID fileId, Integer chunkId, Integer userId, String resolutionTitle) throws MalformedURLException {

        Video video = videoService.getVideo(userId, fileId);
        if(video.isDeleted()) {
            throw new StreamDeletedVideoException();
        }
        Resolution resolution = resolutionService.getResolutionByTitle(resolutionTitle);
        chunkService.checkChunkWithResolutionExists(video, chunkId, resolution);
        Chunk chunk = chunkService.getChunkByVideoAndResolutionAndChunkId(video, resolution, chunkId).orElseThrow(() -> new ChunkMissing(fileId, chunkId));
        incrementDownloads(video, chunk, resolution);
        return getChunkAsResource(fileId, chunkId, resolution);
    }

    @Override
    public List<ChunkDownloadDto> getChunkDownloads(UUID fileId, Integer userId) {

        Video video = videoService.getVideo(userId, fileId);
        List<MediaDownloads> mediaDownloads = mediaDownloadsRepository.findAllByVideo(video);
        Map<Integer, ChunkDownloadDto> downloadsPerChunk = new TreeMap<>();
        for (MediaDownloads mediaDownload : mediaDownloads) {
            Chunk chunk = mediaDownload.getChunk();
            Integer chunkId = chunk.getChunkId();
            Long downloads = mediaDownload.getDownloads();
            Double[] range = new Double[]{chunk.getStartTime(), chunk.getEndTime()};
            ChunkDownloadDto chunkDownloadDto = downloadsPerChunk.getOrDefault(chunkId, new ChunkDownloadDto(chunkId, 0L, range));
            chunkDownloadDto.setDownloads(chunkDownloadDto.getDownloads() + downloads);
            downloadsPerChunk.put(chunkId, chunkDownloadDto);
        }
        List<ChunkDownloadDto> chunkDownloadDtos = new ArrayList<>();
        for (Map.Entry<Integer, ChunkDownloadDto> entry : downloadsPerChunk.entrySet()) {
            chunkDownloadDtos.add(entry.getValue());
        }
        return chunkDownloadDtos;
    }
}
