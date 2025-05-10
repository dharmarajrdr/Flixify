package com.flixify.backend.custom_exceptions;

import com.flixify.backend.model.Resolution;

public class ChunkDoesNotSupportResolution extends RuntimeException {

    public ChunkDoesNotSupportResolution(Integer chunkId, Resolution resolution) {

        super("Chunk with id " + chunkId + " does not support resolution " + resolution);
    }
}
