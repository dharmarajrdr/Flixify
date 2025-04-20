package com.flixify.backend.custom_exceptions;

public class UserNotFound extends Exception {

    public UserNotFound(Integer userId) {

        super("User with ID " + userId + " not found.");
    }
}
