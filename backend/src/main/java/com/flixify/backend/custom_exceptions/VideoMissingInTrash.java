package com.flixify.backend.custom_exceptions;

import com.flixify.backend.model.Video;

public class VideoMissingInTrash extends RuntimeException {

    public VideoMissingInTrash(Video video) {
        super("Video '" + video.getFileId() + "' does not found in trash.");
    }
}
