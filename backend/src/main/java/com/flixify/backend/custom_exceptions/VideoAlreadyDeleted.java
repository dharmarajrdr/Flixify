package com.flixify.backend.custom_exceptions;

import com.flixify.backend.model.Video;

public class VideoAlreadyDeleted extends RuntimeException {

    public VideoAlreadyDeleted(Video video) {
        super("Video '" + video.getFileId() + "' is already deleted");
    }
}
