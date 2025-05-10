package com.flixify.backend.service.interfaces;

import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface VideoSplitterService {

    public VideoSplitterRule getVideoSplitterRule();

    public File splitVideo(Video video, Path videoFilePath, Resolution resolution) throws IOException, InterruptedException;
}
