package com.flixify.backend.service;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.util.LocalDisk;
import org.springframework.stereotype.Service;

import com.flixify.backend.model.User;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.VideoRepository;

import java.io.File;
import java.util.List;
import java.util.UUID;

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

    public Video getVideo(Integer userId, Integer videoId) {

        User user = getUser(userId);
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new VideoNotExist(videoId));
        if (!video.isOwner(user)) {
            throw new PermissionDenied("Unable to fetch the video of another user.");
        }
        return video;
    }

    public Video getVideo(Integer userId, UUID fileId) {

        User user = getUser(userId);
        Video video = videoRepository.findByFileId(fileId).orElseThrow(() -> new VideoNotExist(fileId));
        if (!video.isOwner(user)) {
            throw new PermissionDenied("Unable to fetch the video of another user.");
        }
        return video;
    }

    public Video addVideo(AddVideoDto addVideoDto) {

        Integer chunkCount = 0;

        Integer userId = addVideoDto.getUserId();
        User owner = getUser(userId);

        Video video = addVideoDto.toVideo();
        video.setOwner(owner);
        video.setChunkCount(chunkCount);

        return videoRepository.save(video);
    }

    public void deleteVideo(Integer userId, UUID videoId) {

        Video video = getVideo(userId, videoId);
        videoRepository.delete(video);

        File rawVideoFile = new File(PathConfig.VIDEO_STORAGE_DIRECTORY + "/" + videoId.toString() + ".mp4");
        File chunkFiles = new File(PathConfig.CHUNK_STORAGE_DIRECTORY + "/" + videoId.toString());
        LocalDisk.deleteFile(rawVideoFile.toPath());
        LocalDisk.deleteDirectory(chunkFiles.toPath());
    }

    public Boolean isOwner(Integer userId, UUID fileId) {

        User user = getUser(userId);
        return videoRepository.existsByFileIdAndOwner(fileId, user);
    }
}
