package com.flixify.backend.custom_exceptions;

public class InvalidChunkStatus extends RuntimeException {

    public InvalidChunkStatus(String status) {

        super("'" + status + "' is not a valid status.");
    }
}
