package com.flixify.backend.service.implementations;

import com.flixify.backend.dto.response.ChunkDto;
import com.flixify.backend.dto.response.ManifestDto;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.service.interfaces.ChunkService;
import com.flixify.backend.service.interfaces.ManifestService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManifestServiceImpl implements ManifestService {

    private final ChunkService chunkService;
    private final ResolutionService resolutionService;

    public ManifestServiceImpl(ChunkService chunkService, ResolutionService resolutionService) {
        this.chunkService = chunkService;
        this.resolutionService = resolutionService;
    }

    private ManifestDto generateManifestJson(List<ChunkDto> chunks, UUID videoId) {

        Map<Long, List<ChunkDto>> groupedChunks = chunks.stream().collect(Collectors.groupingBy(c -> c.getResolution().getId()));

        List<ManifestDto.ResolutionEntry> resolutionEntries = new ArrayList<>();

        ManifestDto manifestDto = new ManifestDto();
        manifestDto.setVideoId(videoId);

        for (Map.Entry<Long, List<ChunkDto>> entry : groupedChunks.entrySet()) {
            List<ChunkDto> chunkList = entry.getValue();
            if (chunkList.isEmpty()) {
                continue;
            }

            ChunkDto first = chunkList.getFirst();
            Resolution resolution = first.getResolution();

            List<ManifestDto.ChunkEntry> chunkEntries = chunkList.stream().map(ManifestDto.ChunkEntry::fromChunkDto).toList();

            ManifestDto.ResolutionEntry resolutionEntry = ManifestDto.ResolutionEntry.fromResolution(resolution);
            resolutionEntry.setChunks(chunkEntries);

            resolutionEntries.add(resolutionEntry);
        }

        manifestDto.setResolutions(resolutionEntries);

        return manifestDto;
    }


    @Override
    public ManifestDto getManifest(UUID fileId, Integer userId) {

        List<ChunkDto> chunks = chunkService.getAllChunks(userId, fileId);
        return this.generateManifestJson(chunks, fileId);
    }

    @Override
    public ManifestDto getManifestByResolution(UUID fileId, Integer userId, String resolutionName) {

        Resolution resolution = resolutionService.getResolutionByTitle(resolutionName);
        List<ChunkDto> chunks = chunkService.getAllChunksByResolution(userId, fileId, resolution);
        ManifestDto manifestDto = this.generateManifestJson(chunks, fileId);
        manifestDto.setResolution(manifestDto.getResolutions().getFirst());
        manifestDto.setResolutions(null);
        return manifestDto;
    }
}
