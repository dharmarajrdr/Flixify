package com.flixify.backend.strategy.Notifier;

import com.flixify.backend.dto.NotificationDTO;
import com.flixify.backend.service.interfaces.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    @Override
    public void notify(NotificationDTO notification) {

    }
}
