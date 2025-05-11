package com.flixify.backend.service.implementations;

import com.flixify.backend.config.TrashConstantsConfig;
import com.flixify.backend.dto.NotificationDTO;
import com.flixify.backend.model.User;
import com.flixify.backend.model.Video;
import com.flixify.backend.service.interfaces.NotificationService;
import com.flixify.backend.service.interfaces.TrashCleanerService;
import com.flixify.backend.service.interfaces.VideoDeleterService;
import com.flixify.backend.service.interfaces.VideoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoTrashCleanerServiceImpl implements TrashCleanerService {

    private final VideoService videoService;
    private final NotificationService notificationService;
    private final VideoDeleterService videoDeleterService;

    private static final Integer TRASH_CLEANUP_WARN_CUTOFF_DAYS = TrashConstantsConfig.TRASH_CLEANUP_WARN_CUTOFF_DAYS;
    private static final Integer TRASH_CLEANUP_DELETE_CUTOFF_DAYS = TrashConstantsConfig.TRASH_CLEANUP_DELETE_CUTOFF_DAYS;

    public VideoTrashCleanerServiceImpl(@Qualifier("hardDelete") VideoDeleterService videoDeleterService, @Qualifier("emailNotificationService") NotificationService notificationService, VideoService videoService) {
        this.videoService = videoService;
        this.notificationService = notificationService;
        this.videoDeleterService = videoDeleterService;
    }

    @Override
    @Scheduled(cron = "${trash.cleanup.delete.cron}")
    public void clean() {

        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(TRASH_CLEANUP_DELETE_CUTOFF_DAYS);
        List<Video> expiredVideos = videoService.getDeletedAndLastUpdatedAtBefore(cutoffDate);
        for (Video video : expiredVideos) {
            try {
                videoDeleterService.delete(video);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Scheduled(cron = "${trash.cleanup.warn.cron}")
    public void warnCleanup() {

        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(TRASH_CLEANUP_WARN_CUTOFF_DAYS);
        List<Video> aboutToExpireVideos = videoService.getDeletedAndLastUpdatedAtBefore(cutoffDate);
        int daysLeftToDelete = TRASH_CLEANUP_DELETE_CUTOFF_DAYS - TRASH_CLEANUP_WARN_CUTOFF_DAYS;
        for (Video video : aboutToExpireVideos) {
            try {
                User owner = video.getOwner();
                String userName = owner.getUserName();
                String message = "Hi " + userName + ", your video titled '" + video.getTitle() + "' is scheduled for permanent deletion in " + daysLeftToDelete + " days. Please restore it from the recycle bin if you wish to keep it.";
                NotificationDTO notificationDTO = new NotificationDTO(owner, message);
                notificationService.notify(notificationDTO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
