package com.flixify.backend.service;

import com.flixify.backend.model.Chunk;

import java.nio.file.Path;
import java.util.List;

public interface VideoSplitterService {

    public List<Chunk> splitVideo(Integer videoId, Path videoFilePath);
}
