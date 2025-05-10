package com.flixify.backend.service.implementations;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.dto.request.VideoUploadRequestDto;
import com.flixify.backend.dto.response.VideoUploadedEventDto;
import com.flixify.backend.enums.VideoUploadStatusEnum;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.interfaces.*;
import com.flixify.backend.util.Generator;
import com.flixify.backend.util.LocalDisk;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VideoUploaderImpl implements VideoUploaderService {

    private final VideoService videoService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final VideoSplitterRuleService videoSplitterRuleService;
    private final VideoUploadTaskLogService videoUploadTaskLogService;
    private final VideoChunkTranscoderService videoChunkTranscoderService;

    /**
     * Saves the given file in disk
     *
     * @param videoFile
     * @return Path
     * @throws IOException
     */
    private Path storeInDisk(MultipartFile videoFile, String fileName) throws IOException {

        Path uploadPath = Paths.get(PathConfig.VIDEO_STORAGE_DIRECTORY);
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, videoFile.getBytes());
        return filePath;
    }

    @Override
    @Transactional
    public Path upload(MultipartFile videoFile, VideoUploadRequestDto videoUploadRequestDto) {

        Path filePath = null;

        try {

            UUID uniqueId = Generator.generateUUID();
            filePath = storeInDisk(videoFile, uniqueId.toString() + ".mp4");

            long size = LocalDisk.getFileSize(videoFile);
            double duration = Math.floor(videoService.getVideoDuration(filePath.toFile()));

            Integer userId = videoUploadRequestDto.getUserId();
            String title = videoUploadRequestDto.getTitle();
            String ruleName = videoUploadRequestDto.getVideoSplitterRule();
            VideoSplitterRule videoSplitterRule = videoSplitterRuleService.getVideoSplitterRule(ruleName);

            AddVideoDto addVideoDto = AddVideoDto.builder().title(title).userId(userId).size(size).duration(duration).uniqueId(uniqueId).videoSplitterRule(videoSplitterRule).build();
            Video video = videoService.addVideo(addVideoDto);

            videoUploadTaskLogService.addTaskUpdate(video, VideoUploadStatusEnum.RAW_VIDEO_UPLOADED);

            applicationEventPublisher.publishEvent(new VideoUploadedEventDto(filePath, uniqueId, ruleName, video));
            // videoChunkTranscoderService.splitUploadedVideIntoDifferentResolutions(new VideoUploadedEventDto(filePath, uniqueId, ruleName, video));

            return filePath;

        } catch (Exception e) {
            LocalDisk.deleteFile(filePath);
            throw new RuntimeException(e);
        }
    }

}
