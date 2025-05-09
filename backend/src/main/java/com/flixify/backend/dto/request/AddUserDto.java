package com.flixify.backend.dto.request;

import lombok.Data;

@Data
public class AddUserDto {

    private String userName;

    private String displayName;

    private String email;

    private String phoneNumber;

    private String password;
}
