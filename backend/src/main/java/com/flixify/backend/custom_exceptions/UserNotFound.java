package com.flixify.backend.custom_exceptions;

public class UserNotFound extends RuntimeException {

    public UserNotFound(Integer userId) {

        super("User with ID " + userId + " not found.");
    }
}
