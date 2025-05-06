package com.flixify.backend.strategy.VideoSplitter;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.service.VideoSplitterService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class DurationBasedVideoSplitter implements VideoSplitterService {

    @Override
    public List<Chunk> splitVideo(Integer videoId, Path videoFilePath) {

        return List.of();
    }
}
