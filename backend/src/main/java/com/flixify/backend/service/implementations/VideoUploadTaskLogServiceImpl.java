package com.flixify.backend.service.implementations;

import com.flixify.backend.enums.VideoUploadStatusEnum;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoUploadTaskLog;
import com.flixify.backend.repository.VideoUploadTaskLogRepository;
import com.flixify.backend.service.interfaces.VideoUploadTaskLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VideoUploadTaskLogServiceImpl implements VideoUploadTaskLogService {

    private final VideoUploadTaskLogRepository videoUploadTaskLogRepository;

    @Override
    @Transactional
    public void addTaskUpdate(Video video, VideoUploadStatusEnum videoUploadStatusEnum) {

        VideoUploadTaskLog videoUploadTaskLog = new VideoUploadTaskLog();
        videoUploadTaskLog.setVideo(video);
        videoUploadTaskLog.setStatus(videoUploadStatusEnum);
        videoUploadTaskLogRepository.save(videoUploadTaskLog);
    }
}
