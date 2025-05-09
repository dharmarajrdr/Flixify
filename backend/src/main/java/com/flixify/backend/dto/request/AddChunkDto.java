package com.flixify.backend.dto.request;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.ChunkStatus;
import lombok.Data;

@Data
public class AddChunkDto {

    private Integer pixel;

    private Double startTime;

    private Double endTime;

    private String fileId;    // UUID = Name of the file

    private String chunkStatus;

    private Double size;

    public Chunk toChunk() {

        Chunk chunk = new Chunk();
        chunk.setStartTime(startTime);
        chunk.setEndTime(endTime);
        chunk.setSize(size);
        return chunk;
    }

}
