package com.flixify.backend.service;

import com.flixify.backend.custom_exceptions.VideoUploadFailed;
import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.dto.request.VideoUploadRequestDto;
import com.flixify.backend.factory.VideoSplitterFactory;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.util.Generator;
import org.mp4parser.IsoFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class VideoUploaderImpl implements VideoUploader {

    @Value("${video.storage.directory}")
    private String VIDEO_STORAGE_DIRECTORY;

    private final VideoService videoService;
    private final ChunkService chunkService;
    private final VideoSplitterRuleService videoSplitterRuleService;

    public VideoUploaderImpl(VideoService videoService, ChunkService chunkService, VideoSplitterRuleService videoSplitterRuleService) {
        this.videoService = videoService;
        this.chunkService = chunkService;
        this.videoSplitterRuleService = videoSplitterRuleService;
    }

    /**
     * Saves the given file in disk
     *
     * @param videoFile
     * @return Path
     * @throws IOException
     */
    private Path storeInDisk(MultipartFile videoFile, String fileName) throws IOException {

        Path uploadPath = Paths.get(VIDEO_STORAGE_DIRECTORY);
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, videoFile.getBytes());
        return filePath;
    }

    /**
     * Delete the video file from the disk
     * @param videoFile
     */
    private void deleteVideo(Path videoFile) {

        if(videoFile != null) {
            File file = videoFile.toFile();
            file.delete();
        }
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
    private AddVideoDto getAddVideoDto(String title, Integer userId, long size, double duration, UUID uniqueId, VideoSplitterRule videoSplitterRule) {

        AddVideoDto addVideoDto = new AddVideoDto();
        addVideoDto.setTitle(title);
        addVideoDto.setUserId(userId);
        addVideoDto.setSize(size);
        addVideoDto.setDuration(duration);
        addVideoDto.setUniqueId(uniqueId);
        addVideoDto.setVideoSplitterRule(videoSplitterRule);
        return addVideoDto;
    }

    @Override
    @Transactional
    public Path upload(MultipartFile videoFile, VideoUploadRequestDto videoUploadRequestDto) {

        Path filePath = null;

        try {

            UUID uniqueId = Generator.generateUUID();
            filePath = storeInDisk(videoFile, uniqueId.toString() + ".mp4");

            long size = getFileSize(videoFile);
            double duration = 0; /* getDuration(filePath.toAbsolutePath().toString()); */

            Integer userId = videoUploadRequestDto.getUserId();
            String title = videoUploadRequestDto.getTitle();
            String ruleName = videoUploadRequestDto.getVideoSplitterRule();

            VideoSplitterRule videoSplitterRule = videoSplitterRuleService.getVideoSplitterRule(ruleName);
            VideoSplitterService videoSplitterService = VideoSplitterFactory.getVideoSplitter(ruleName);

            AddVideoDto addVideoDto = getAddVideoDto(title, userId, size, duration, uniqueId, videoSplitterRule);
            Video video = videoService.addVideo(addVideoDto);

            List<Chunk> splittedChunks = videoSplitterService.splitVideo(video, filePath);
            List<Chunk> persistedChunks = chunkService.saveAll(splittedChunks);

            return filePath;

        } catch (IOException e) {
            deleteVideo(filePath);
            throw new VideoUploadFailed(e);
        } catch (InterruptedException e) {
            deleteVideo(filePath);
            throw new RuntimeException(e);
        }
    }
}
