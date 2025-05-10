package com.flixify.backend.service.interfaces;

import com.flixify.backend.model.Account;

public interface AccountService {

    public Account addAccount(String email, String phoneNumber, String password);
}
