package com.flixify.backend.service;

import org.springframework.stereotype.Service;

import com.flixify.backend.custom_exceptions.UserNotFound;
import com.flixify.backend.model.User;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.VideoRepository;

@Service
public class VideoService {

    private VideoRepository videoRepository;
    private UserService userService;

    public VideoService(VideoRepository videoRepository, UserService userService) {
        this.videoRepository = videoRepository;
        this.userService = userService;
    }

    public Video addVideo(String title, Double duration, Long size, Integer chunkCount, Integer userId) throws UserNotFound {

        User owner = userService.findUserById(userId);
        Video video = new Video(title, duration, size, chunkCount, owner);
        return videoRepository.save(video);
    }
}
