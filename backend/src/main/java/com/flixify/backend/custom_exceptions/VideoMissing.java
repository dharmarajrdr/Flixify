package com.flixify.backend.custom_exceptions;

public class VideoMissing extends RuntimeException {

    public VideoMissing(String filePath)  {

        super("Video file '" + filePath + "' not found.");
    }
}
