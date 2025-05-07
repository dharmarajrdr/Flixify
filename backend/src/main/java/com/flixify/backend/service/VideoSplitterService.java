package com.flixify.backend.service;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.VideoSplitterRule;

import java.nio.file.Path;
import java.util.List;

public interface VideoSplitterService {

    public VideoSplitterRule getVideoSplitterRule();

    public List<Chunk> splitVideo(Integer videoId, Path videoFilePath);
}
