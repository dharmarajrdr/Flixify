package com.flixify.backend.custom_exceptions;

public class StreamDeletedVideoException extends RuntimeException {

    public StreamDeletedVideoException() {

        super("Unable to stream this video. It has been deleted by the owner.");
    }
}
