package com.flixify.backend.custom_exceptions;

public class PermissionDenied extends RuntimeException {

    public PermissionDenied(String message) {

        super("Permission Denied! " + message);
    }
}
