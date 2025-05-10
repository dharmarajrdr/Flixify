package com.flixify.backend.service;

import com.flixify.backend.model.Resolution;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ResolutionConverterServiceImpl implements ResolutionConverterService {

    @Override
    public void convertResolution(Resolution resolution, File source, File target) throws IOException, InterruptedException {

        ProcessBuilder builder = new ProcessBuilder(
                "ffmpeg", "-i", source.getAbsolutePath(),
                "-vf", "scale=-2:" + resolution.getPixel(),
                "-c:a", "copy",
                target.getAbsolutePath()
        );

        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Failed to convert to resolution: " + resolution.getTitle());
        }
    }
}
