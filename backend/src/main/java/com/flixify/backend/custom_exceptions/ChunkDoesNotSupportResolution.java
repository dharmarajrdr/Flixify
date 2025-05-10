package com.flixify.backend.custom_exceptions;

import com.flixify.backend.model.Resolution;

public class ChunkDoesNotSupportResolution extends RuntimeException {

    public ChunkDoesNotSupportResolution(Resolution resolution) {

        super("Chunk does not support resolution '" + resolution.getTitle() + "'.");
    }
}
