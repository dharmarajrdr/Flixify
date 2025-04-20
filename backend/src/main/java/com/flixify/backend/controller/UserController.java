package com.flixify.backend.controller;

import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.flixify.backend.custom_exceptions.AccountNotFound;
import com.flixify.backend.dto.request.AddUserDto;
import com.flixify.backend.model.User;
import com.flixify.backend.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> addUser(@RequestBody AddUserDto addUserDto) throws AccountNotFound {

        User user = userService.addUser(addUserDto);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "User created successfully.", user, null);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
