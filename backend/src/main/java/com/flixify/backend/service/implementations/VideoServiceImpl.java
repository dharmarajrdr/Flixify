package com.flixify.backend.service.implementations;

import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.custom_exceptions.VideoAlreadyDeleted;
import com.flixify.backend.custom_exceptions.VideoMissingInTrash;
import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.service.interfaces.UserService;
import com.flixify.backend.service.interfaces.VideoDeleterService;
import com.flixify.backend.service.interfaces.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.flixify.backend.model.User;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.VideoRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VideoServiceImpl implements VideoService {

    private final UserService userService;
    private final VideoDeleterService videoDeleterService;
    private final VideoRepository videoRepository;

    private User getUser(Integer userId) {

        return userService.findUserById(userId);
    }

    @Override
    public List<Video> getVideosByUserId(Integer userId) {

        User owner = getUser(userId);
        return videoRepository.findByOwner(owner);
    }

    @Override
    public List<Video> getDeletedAndLastUpdatedAtBefore(LocalDateTime date) {

        return videoRepository.findByIsDeletedTrueAndLastUpdatedAtBefore(date);
    }

    @Override
    public Video getVideo(Integer userId, UUID fileId) {

        User user = getUser(userId);
        Video video = videoRepository.findByFileId(fileId).orElseThrow(() -> new VideoNotExist(fileId));
        if (!video.isOwner(user)) {
            throw new PermissionDenied("Unable to fetch the video of another user.");
        }
        return video;
    }

    @Override
    public Video addVideo(AddVideoDto addVideoDto) {

        Integer chunkCount = 0;

        Integer userId = addVideoDto.getUserId();
        User owner = getUser(userId);

        Video video = addVideoDto.toVideo();
        video.setOwner(owner);
        video.setChunkCount(chunkCount);

        return videoRepository.save(video);
    }

    public double getVideoDuration(File file) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(
                "ffprobe", "-v", "error", "-show_entries",
                "format=duration", "-of", "default=noprint_wrappers=1:nokey=1",
                file.getAbsolutePath()
        );

        Process process = builder.start();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
        );
        String line = reader.readLine();

        int exitCode = process.waitFor();
        if (exitCode != 0 || line == null) {

            throw new RuntimeException("Failed to get duration for: " + file.getName());
        }

        return Double.parseDouble(line.trim());
    }

    @Override
    public void deleteVideo(Integer userId, UUID videoId) {

        Video video = getVideo(userId, videoId);
        if(video.isDeleted()) {
            throw new VideoAlreadyDeleted(video);
        }
        videoDeleterService.delete(video);
    }

    @Override
    public void recoverVideo(Integer userId, UUID fileId) {

        Video video = getVideo(userId, fileId);
        if (!video.isDeleted()) {
            throw new VideoMissingInTrash(video);
        }
        videoDeleterService.recover(video);
    }

    public Boolean isOwner(Integer userId, UUID fileId) {

        User user = getUser(userId);
        return videoRepository.existsByFileIdAndOwner(fileId, user);
    }
}
