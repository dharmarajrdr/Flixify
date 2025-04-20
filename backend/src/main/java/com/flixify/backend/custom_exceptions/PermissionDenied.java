package com.flixify.backend.custom_exceptions;

public class PermissionDenied extends Exception {

    public PermissionDenied(String message) {

        super("Permission Denied! " + message);
    }
}
