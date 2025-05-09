package com.flixify.backend.custom_exceptions;

import java.util.UUID;

public class VideoNotExist extends RuntimeException {

    public VideoNotExist(Integer videoId) {

        super("Video with ID " + videoId + " does not exist.");
    }

    public VideoNotExist(UUID fileId) {

        super("Video with ID " + fileId.toString() + " does not exist.");
    }
}
