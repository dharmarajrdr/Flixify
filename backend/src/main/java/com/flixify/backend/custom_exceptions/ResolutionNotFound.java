package com.flixify.backend.custom_exceptions;

public class ResolutionNotFound extends RuntimeException {

    public ResolutionNotFound(Integer pixel) {

        super("Resolution with '" + pixel + "' pixel does not exist.");
    }

    public ResolutionNotFound(String title) {

        super("Resolution '" + title + "' does not exist.");
    }
}
