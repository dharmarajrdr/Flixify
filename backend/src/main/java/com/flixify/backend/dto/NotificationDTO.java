package com.flixify.backend.dto;

import com.flixify.backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDTO {

    private User user;

    private String message;
}
