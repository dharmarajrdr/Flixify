package com.flixify.backend.custom_exceptions;

public class AccountNotFound extends Exception {

    public AccountNotFound(Integer accountId) {
        super("Account with ID " + accountId + " does not exist.");
    }
}
