package com.flixify.backend.dto.response;

import com.flixify.backend.model.Chunk;
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

    private Double size;

    public ChunkDto(Chunk chunk) {

        this.chunkId = chunk.getChunkId();
        this.resolution = chunk.getResolution();
        this.range = new Double[]{chunk.getStartTime(), chunk.getEndTime()};
        this.resourceUrl = "https://flixify.com/v1/api/video/" + chunk.getVideo().getFileId().toString() + "/chunk/" + chunk.getChunkId() + "/" + resolution.getTitle();
        this.size = chunk.getSize();
    }
}
