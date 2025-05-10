package com.flixify.backend.service.implementations;

import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.dto.request.VideoUploadRequestDto;
import com.flixify.backend.factory.VideoSplitterFactory;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.interfaces.*;
import com.flixify.backend.util.LocalDisk;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class VideoChunkTranscoderServiceImpl implements VideoChunkTranscoderService {

    private final VideoService videoService;
    private final VideoSplitterRuleService videoSplitterRuleService;
    private final ResolutionService resolutionService;
    private final ChunkService chunkService;

    public VideoChunkTranscoderServiceImpl(VideoService videoService, VideoSplitterRuleService videoSplitterRuleService, ResolutionService resolutionService, ChunkService chunkService) {
        this.videoService = videoService;
        this.videoSplitterRuleService = videoSplitterRuleService;
        this.resolutionService = resolutionService;
        this.chunkService = chunkService;
    }

    @Async
    @Override
    public void splitUploadedVideIntoDifferentResolutions(MultipartFile videoFile, Path filePath, VideoUploadRequestDto videoUploadRequestDto, UUID uniqueId) throws IOException, InterruptedException {

        long size = LocalDisk.getFileSize(videoFile);
        double duration = Math.floor(videoService.getVideoDuration(filePath.toFile()));

        Integer userId = videoUploadRequestDto.getUserId();
        String title = videoUploadRequestDto.getTitle();
        String ruleName = videoUploadRequestDto.getVideoSplitterRule();

        VideoSplitterRule videoSplitterRule = videoSplitterRuleService.getVideoSplitterRule(ruleName);
        VideoSplitterService videoSplitterService = VideoSplitterFactory.getVideoSplitter(ruleName);

        AddVideoDto addVideoDto = AddVideoDto.builder().title(title).userId(userId).size(size).duration(duration).uniqueId(uniqueId).videoSplitterRule(videoSplitterRule).build();
        Video video = videoService.addVideo(addVideoDto);

        Resolution rawFileResolution = resolutionService.getFileResolution(filePath.toFile());
        Integer rawFilePixel = rawFileResolution.getPixel();

        File chunksDirectory = videoSplitterService.splitVideo(video, filePath, rawFileResolution);
        List<Chunk> splittedChunks = chunkService.getChunksMetaData(chunksDirectory, video, rawFileResolution);
        chunkService.saveAll(splittedChunks);

        List<Resolution> resolutionsToConvert = resolutionService.getAllResolutionsLessThanPixel(rawFilePixel);
        for (Resolution resolution : resolutionsToConvert) {
            if (!resolution.getTitle().equals(rawFileResolution.getTitle())) {
                File transcodedChunksDirectory = resolutionService.transcodeChunks(resolution, uniqueId, rawFileResolution);
                List<Chunk> transcodedChunks = chunkService.getChunksMetaData(transcodedChunksDirectory, video, resolution);
                chunkService.saveAll(transcodedChunks);
            }
        }
    }
}
