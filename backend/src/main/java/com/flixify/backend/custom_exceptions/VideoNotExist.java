package com.flixify.backend.custom_exceptions;

public class VideoNotExist extends Exception {

    public VideoNotExist(Integer videoId) {

        super("Video with ID " + videoId + " does not exist.");
    }
}
