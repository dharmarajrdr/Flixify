package com.flixify.backend.service;

import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.custom_exceptions.VideoNotExist;
import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.model.User;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.VideoRepository;

import java.util.List;

@Service
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserService userService;

    public VideoService(VideoRepository videoRepository, UserService userService) {
        this.videoRepository = videoRepository;
        this.userService = userService;
    }

    private User getUser(Integer userId) {

        return userService.findUserById(userId);
    }

    public List<Video> getVideosByUserId(Integer userId) {

        User owner = getUser(userId);
        return videoRepository.findByOwner(owner);
    }

    public Video getVideo(Integer userId, Integer videoId) throws UserNotFound, VideoNotExist, PermissionDenied {

        User user = getUser(userId);
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new VideoNotExist(videoId));
        if (!video.getOwner().equals(user)) {
            throw new PermissionDenied("Unable to fetch the video of another user.");
        }
        return video;
    }

    public Video addVideo(String title, Double duration, Long size, Integer chunkCount, Integer userId) throws UserNotFound {

        User owner = getUser(userId);
        Video video = new Video(title, duration, size, chunkCount, owner);
        return videoRepository.save(video);
    }
}
