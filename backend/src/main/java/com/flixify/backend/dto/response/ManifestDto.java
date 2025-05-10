package com.flixify.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.flixify.backend.model.Resolution;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManifestDto {

    public UUID videoId;

    public List<ResolutionEntry> resolutions;

    public ResolutionEntry resolution;

    @Data
    public static class ResolutionEntry {
        public Long id;
        public String title;
        public int pixel;
        public List<ChunkEntry> chunks;

        public static ResolutionEntry fromResolution(Resolution resolution) {

            ResolutionEntry resolutionEntry = new ResolutionEntry();
            resolutionEntry.setId(resolution.getId());
            resolutionEntry.setTitle(resolution.getTitle());
            resolutionEntry.setPixel(resolution.getPixel());
            return resolutionEntry;
        }
    }

    @Data
    public static class ChunkEntry {
        public int id;
        public Double[] range;
        public String url;
        public double size;

        public static ChunkEntry fromChunkDto(ChunkDto chunkDto) {

            ChunkEntry chunkEntry = new ChunkEntry();
            chunkEntry.setId(chunkEntry.getId());
            chunkEntry.setSize(chunkDto.getSize());
            chunkEntry.setRange(chunkDto.getRange());
            chunkEntry.setUrl(chunkDto.getResourceUrl());
            return chunkEntry;
        }
    }
}
