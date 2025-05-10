package com.flixify.backend.service.implementations;

import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.AccountNotFound;
import com.flixify.backend.model.Account;
import com.flixify.backend.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account addAccount(String email, String phoneNumber, String password) {

        Account account = new Account(email, phoneNumber, password);
        return accountRepository.save(account);
    }

    public Account findAccountById(Integer accountId) throws AccountNotFound {

        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFound(accountId));
    }
}
