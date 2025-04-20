package com.flixify.backend.controller;

import com.flixify.backend.dto.response.ResponseDto;
import com.flixify.backend.enums.ResponseStatusEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.flixify.backend.dto.request.AddUserDto;
import com.flixify.backend.model.User;
import com.flixify.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseDto> addUser(@RequestBody AddUserDto addUserDto) {

        User user = userService.addUser(addUserDto);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "User created successfully.", user, null);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseDto> getUser(@RequestParam Integer userId) {

        User user = userService.findUserById(userId);
        ResponseDto responseDto = new ResponseDto(ResponseStatusEnum.SUCCESS, "User details fetched successfully.", user, null);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
