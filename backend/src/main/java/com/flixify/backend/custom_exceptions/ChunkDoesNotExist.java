package com.flixify.backend.custom_exceptions;

import java.util.UUID;

public class ChunkDoesNotExist extends RuntimeException {

    public ChunkDoesNotExist(Integer chunkId, UUID fileId) {

        super("Chunk with id '" + chunkId + "' does not exist in file '" + fileId + "'");
    }
}
