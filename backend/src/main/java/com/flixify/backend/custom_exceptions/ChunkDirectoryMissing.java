package com.flixify.backend.custom_exceptions;

import com.flixify.backend.model.Resolution;

import java.util.UUID;

public class ChunkDirectoryMissing extends RuntimeException {

    public ChunkDirectoryMissing(UUID uuid, Resolution resolution) {

        super("Chunks with " + resolution.getTitle() + " resolution are missing for " + uuid + " video.");
    }
}
