package com.flixify.backend.service;

import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.AccountNotFound;
import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.dto.request.AddUserDto;
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

    public User addUser(AddUserDto addUserDto) throws AccountNotFound {

        String userName = addUserDto.getUserName();
        String displayName = addUserDto.getDisplayName();

        String email = addUserDto.getEmail();
        String phoneNumber = addUserDto.getPhoneNumber();
        String password = addUserDto.getPassword();

        Account account = accountService.addAccount(email, phoneNumber, password);
        User user = new User(userName, displayName, account);
        return userRepository.save(user);
    }

    public User findUserById(Integer userId) throws UserNotFound {

        return userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
    }
}
