package com.flixify.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrashConstantsConfig {

    @Value("${trash.cleanup.delete.cutoff-days}")
    private int trashCleanupDeleteCutoffDays;

    @Value("${trash.cleanup.warn.cutoff-days}")
    private int trashCleanupWarningCutoffDays;

    public static Integer TRASH_CLEANUP_DELETE_CUTOFF_DAYS;

    public static Integer TRASH_CLEANUP_WARN_CUTOFF_DAYS;

    @PostConstruct
    public void init() {

        TRASH_CLEANUP_DELETE_CUTOFF_DAYS = trashCleanupDeleteCutoffDays;
        TRASH_CLEANUP_WARN_CUTOFF_DAYS = trashCleanupWarningCutoffDays;
    }
}
