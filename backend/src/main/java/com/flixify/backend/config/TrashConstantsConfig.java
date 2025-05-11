package com.flixify.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrashConstantsConfig {

    @Value("${trash.cleanup.delete.cutoff-days}")
    private int trashCleanupCutoffDays;

    public static Integer TRASH_CLEANUP_CUTOFF_DAYS;

    @PostConstruct
    public void init() {

        TRASH_CLEANUP_CUTOFF_DAYS = trashCleanupCutoffDays;
    }
}
