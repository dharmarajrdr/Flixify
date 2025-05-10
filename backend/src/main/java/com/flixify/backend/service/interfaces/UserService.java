package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.request.AddUserDto;
import com.flixify.backend.model.User;

public interface UserService {

    public User addUser(AddUserDto addUserDto);

    public User findUserById(Integer userId);
}
