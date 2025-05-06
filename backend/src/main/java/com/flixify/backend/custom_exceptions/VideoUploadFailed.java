package com.flixify.backend.custom_exceptions;

public class VideoUploadFailed extends RuntimeException {

    public VideoUploadFailed(Exception e) {
        super("Video upload failed. " + e.getMessage());
    }
}
