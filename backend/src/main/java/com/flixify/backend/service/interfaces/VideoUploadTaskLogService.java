package com.flixify.backend.service.interfaces;

import com.flixify.backend.enums.VideoUploadStatusEnum;
import com.flixify.backend.model.Video;

public interface VideoUploadTaskLogService {

    public void addTaskUpdate(Video video, VideoUploadStatusEnum videoUploadStatusEnum);
}
