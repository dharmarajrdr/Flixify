package com.flixify.backend.custom_exceptions;

public class UnableToSplitException extends RuntimeException {

    public UnableToSplitException(String message) {
        super(message);
    }
}
