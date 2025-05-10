package com.flixify.backend.service.implementations;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.PermissionDenied;
import com.flixify.backend.custom_exceptions.VideoNotExist;
import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.service.interfaces.VideoService;
import com.flixify.backend.util.LocalDisk;
import org.springframework.stereotype.Service;

import com.flixify.backend.model.User;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.VideoRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final UserService userService;

    public VideoServiceImpl(VideoRepository videoRepository, UserService userService) {
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
