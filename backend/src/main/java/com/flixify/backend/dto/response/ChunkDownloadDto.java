package com.flixify.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChunkDownloadDto {

    private Integer chunkId;

    private Long downloads;
}
