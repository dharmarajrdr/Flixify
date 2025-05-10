package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.response.VideoUploadedEventDto;

import java.io.IOException;

public interface VideoChunkTranscoderService {

    public void splitUploadedVideIntoDifferentResolutions(VideoUploadedEventDto videoUploadedEventDto) throws IOException, InterruptedException;
}
