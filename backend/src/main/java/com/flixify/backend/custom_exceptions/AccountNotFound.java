package com.flixify.backend.custom_exceptions;

public class AccountNotFound extends RuntimeException {

    public AccountNotFound(Integer accountId) {
        super("Account with ID " + accountId + " does not exist.");
    }
}
