package com.flixify.backend.custom_exceptions;

import java.util.UUID;

public class ChunkMissing extends RuntimeException {

    public ChunkMissing(UUID fileId, Integer chunkId) {
        super("Chunk with id " + chunkId + " not found in file " + fileId);
    }
}
