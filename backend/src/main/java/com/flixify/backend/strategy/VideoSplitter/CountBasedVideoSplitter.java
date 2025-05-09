package com.flixify.backend.strategy.VideoSplitter;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.VideoSplitterRuleService;
import com.flixify.backend.service.VideoSplitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class CountBasedVideoSplitter implements VideoSplitterService {

    @Autowired
    private VideoSplitterRuleService videoSplitterRuleService;

    @Override
    public VideoSplitterRule getVideoSplitterRule() {

        return videoSplitterRuleService.getVideoSplitterRule("COUNT_BASED_VIDEO_SPLITTER");
    }

    @Override
    public List<Chunk> splitVideo(Video video, Path videoFilePath) {

        return List.of();
    }
}

