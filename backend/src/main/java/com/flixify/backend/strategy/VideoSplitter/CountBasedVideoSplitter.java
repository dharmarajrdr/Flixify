package com.flixify.backend.strategy.VideoSplitter;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.service.VideoSplitterService;

import java.nio.file.Path;
import java.util.List;

public class CountBasedVideoSplitter implements VideoSplitterService {

    @Override
    public List<Chunk> splitVideo(Integer videoId, Path videoFilePath) {

        return List.of();
    }
}

