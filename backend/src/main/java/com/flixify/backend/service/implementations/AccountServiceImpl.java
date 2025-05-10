package com.flixify.backend.service.implementations;

import com.flixify.backend.service.interfaces.AccountService;
import org.springframework.stereotype.Service;

import com.flixify.backend.model.Account;
import com.flixify.backend.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account addAccount(String email, String phoneNumber, String password) {

        Account account = new Account(email, phoneNumber, password);
        return accountRepository.save(account);
    }
}
