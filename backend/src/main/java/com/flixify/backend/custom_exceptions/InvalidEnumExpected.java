package com.flixify.backend.custom_exceptions;

public class InvalidEnumExpected extends RuntimeException {

    public InvalidEnumExpected(String message) {

        super(message);
    }
}
