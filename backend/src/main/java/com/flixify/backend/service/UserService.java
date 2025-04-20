package com.flixify.backend.service;

import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.AccountNotFound;
import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.model.Account;
import com.flixify.backend.model.User;
import com.flixify.backend.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;
    private AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    public User addUser(String userName, String displayName, Integer accountId) throws AccountNotFound {

        Account account = accountService.findAccountById(accountId);
        User user = new User(userName, displayName, account);
        return userRepository.save(user);
    }

    public User findUserById(Integer userId) throws UserNotFound {

        return userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
    }
}
