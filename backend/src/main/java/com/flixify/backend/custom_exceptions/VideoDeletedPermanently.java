package com.flixify.backend.custom_exceptions;

import com.flixify.backend.model.Video;

public class VideoDeletedPermanently extends RuntimeException {

    public VideoDeletedPermanently(Video video) {
        super("Video " + video.getId() + " was deleted permanently.");
    }
}
