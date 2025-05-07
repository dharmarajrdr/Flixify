package com.flixify.backend.strategy.VideoSplitter;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.VideoSplitterRuleService;
import com.flixify.backend.service.VideoSplitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class SizeBasedVideoSplitter implements VideoSplitterService {

    @Autowired
    private VideoSplitterRuleService videoSplitterRuleService;

    @Override
    public VideoSplitterRule getVideoSplitterRule() {

        return videoSplitterRuleService.getVideoSplitterRule("SIZE_BASED_VIDEO_SPLITTER");
    }

    @Override
    public List<Chunk> splitVideo(Integer videoId, Path videoFilePath) {

        return List.of();
    }
}

