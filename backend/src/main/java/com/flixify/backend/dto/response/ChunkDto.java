package com.flixify.backend.dto.response;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.ChunkStatus;
import com.flixify.backend.model.Resolution;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChunkDto {

    private Integer chunkId;

    private Resolution resolution;

    private Double[] range;

    private String resourceUrl;

    private ChunkStatus chunkStatus;

    private Double size;

    public ChunkDto(Chunk chunk) {

        this.chunkId = chunk.getChunkId();
        this.resolution = chunk.getResolution();
        this.range = new Double[]{chunk.getStartTime(), chunk.getEndTime()};
        this.resourceUrl = "https://flixify.com/video/" + chunk.getVideo().getId() + "/chunk/" + chunk.getChunkId();
        this.chunkStatus = chunk.getChunkStatus();
        this.size = chunk.getSize();
    }

}
