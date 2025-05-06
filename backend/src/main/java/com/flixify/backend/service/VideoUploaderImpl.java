package com.flixify.backend.service;

import com.flixify.backend.custom_exceptions.VideoUploadFailed;
import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.dto.request.VideoUploadRequestDto;
import org.mp4parser.IsoFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoUploaderImpl implements VideoUploader {

    @Value("${video.storage.directory}")
    private String VIDEO_STORAGE_DIRECTORY;

    private final VideoService videoService;

    public VideoUploaderImpl(VideoService videoService) {
        this.videoService = videoService;
    }

    /**
     * Saves the given file in disk
     *
     * @param videoFile
     * @return Path
     * @throws IOException
     */
    private Path storeInDisk(MultipartFile videoFile) throws IOException {

        Path uploadPath = Paths.get(VIDEO_STORAGE_DIRECTORY);
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(videoFile.getOriginalFilename());
        Files.write(filePath, videoFile.getBytes());
        return filePath;
    }

    /**
     * Get the size of the given video file
     *
     * @param videoFile
     * @return
     */
    private long getFileSize(MultipartFile videoFile) {

        return videoFile.getSize();
    }

    /**
     * Get the duration of the given video file
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private double getDuration(String filePath) throws IOException {

        FileInputStream fis = new FileInputStream(new File(filePath));
        IsoFile isoFile = new IsoFile(fis.getChannel());
        long duration = isoFile.getMovieBox().getMovieHeaderBox().getDuration();
        long timeScale = isoFile.getMovieBox().getMovieHeaderBox().getTimescale();
        double durationInSeconds = (double) duration / timeScale;
        return durationInSeconds;
    }

    /**
     * Construct the DTO to save the meta data of video
     *
     * @param title
     * @param userId
     * @param size
     * @param duration
     * @return
     */
    private AddVideoDto getAddVideoDto(String title, Integer userId, long size, double duration) {

        AddVideoDto addVideoDto = new AddVideoDto();
        addVideoDto.setTitle(title);
        addVideoDto.setUserId(userId);
        addVideoDto.setSize(size);
        addVideoDto.setDuration(duration);
        return addVideoDto;
    }

    @Override
    public Path upload(MultipartFile videoFile, VideoUploadRequestDto videoUploadRequestDto) {

        try {

            Path filePath = storeInDisk(videoFile);

            long size = getFileSize(videoFile);
            double duration = getDuration(filePath.toAbsolutePath().toString());

            Integer userId = videoUploadRequestDto.getUserId();
            String title = videoUploadRequestDto.getTitle();

            AddVideoDto addVideoDto = getAddVideoDto(title, userId, size, duration);
            videoService.addVideo(addVideoDto);

            return filePath;

        } catch (IOException e) {
            throw new VideoUploadFailed(e);
        }
    }
}
