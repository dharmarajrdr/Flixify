package com.flixify.backend.service.implementations;

import com.flixify.backend.dto.response.VideoUploadedEventDto;
import com.flixify.backend.enums.VideoUploadStatusEnum;
import com.flixify.backend.factory.VideoSplitterFactory;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;
import com.flixify.backend.service.interfaces.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class VideoChunkTranscoderServiceImpl implements VideoChunkTranscoderService {

    private final ChunkService chunkService;
    private final ResolutionService resolutionService;
    private final VideoUploadTaskLogService videoUploadTaskLogService;

    public VideoChunkTranscoderServiceImpl(ResolutionService resolutionService, ChunkService chunkService, VideoUploadTaskLogService videoUploadTaskLogService) {
        this.chunkService = chunkService;
        this.resolutionService = resolutionService;
        this.videoUploadTaskLogService = videoUploadTaskLogService;
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void splitUploadedVideIntoDifferentResolutions(VideoUploadedEventDto videoUploadedEventDto) throws IOException, InterruptedException {
        Path rawVideoFilePath = videoUploadedEventDto.getRawVideoFilePath();
        UUID uniqueId = videoUploadedEventDto.getUniqueId();
        String ruleName = videoUploadedEventDto.getRuleName();
        Video video = videoUploadedEventDto.getVideo();

        videoUploadTaskLogService.addTaskUpdate(video, VideoUploadStatusEnum.SPLITTING_VIDEO);

        VideoSplitterService videoSplitterService = VideoSplitterFactory.getVideoSplitter(ruleName);

        Resolution rawFileResolution = resolutionService.getFileResolution(rawVideoFilePath.toFile());
        Integer rawFilePixel = rawFileResolution.getPixel();

        File chunksDirectory = videoSplitterService.splitVideo(video, rawVideoFilePath, rawFileResolution);

        videoUploadTaskLogService.addTaskUpdate(video, VideoUploadStatusEnum.VIDEO_SPLIT_SUCCESS);

        List<Chunk> splittedChunks = chunkService.getChunksMetaData(chunksDirectory, video, rawFileResolution);
        chunkService.saveAll(splittedChunks);

        videoUploadTaskLogService.addTaskUpdate(video, VideoUploadStatusEnum.RESOLUTION_CONVERTING);

        List<Resolution> resolutionsToConvert = resolutionService.getAllResolutionsLessThanPixel(rawFilePixel);
        for (Resolution resolution : resolutionsToConvert) {
            if (!resolution.getTitle().equals(rawFileResolution.getTitle())) {
                File transcodedChunksDirectory = resolutionService.transcodeChunks(resolution, uniqueId, rawFileResolution);
                List<Chunk> transcodedChunks = chunkService.getChunksMetaData(transcodedChunksDirectory, video, resolution);
                chunkService.saveAll(transcodedChunks);
            }
        }

        videoUploadTaskLogService.addTaskUpdate(video, VideoUploadStatusEnum.RESOLUTION_CONVERTED);

    }
}
