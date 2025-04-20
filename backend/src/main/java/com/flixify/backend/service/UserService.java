package com.flixify.backend.service;

import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.dto.request.AddUserDto;
import com.flixify.backend.model.Account;
import com.flixify.backend.model.User;
import com.flixify.backend.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountService accountService;

    public UserService(UserRepository userRepository, AccountService accountService) {
        this.userRepository = userRepository;
        this.accountService = accountService;
    }

    @Transactional
    public User addUser(AddUserDto addUserDto) {

        String userName = addUserDto.getUserName();
        String displayName = addUserDto.getDisplayName();

        String email = addUserDto.getEmail();
        String phoneNumber = addUserDto.getPhoneNumber();
        String password = addUserDto.getPassword();

        Account account = accountService.addAccount(email, phoneNumber, password);
        User user = new User(userName, displayName, account);
        return userRepository.save(user);
    }

    public User findUserById(Integer userId) {

        return userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
    }
}
