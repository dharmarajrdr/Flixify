package com.flixify.backend.service;

import com.flixify.backend.custom_exceptions.ResolutionNotFound;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.repository.ResolutionRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ResolutionService {

    private ResolutionRepository resolutionRepository;

    public ResolutionService(ResolutionRepository resolutionRepository) {
        this.resolutionRepository = resolutionRepository;
    }

    public Resolution getResolution(Integer pixel) {

        return resolutionRepository.findByPixel(pixel).orElseThrow(()-> new ResolutionNotFound(pixel));
    }

    public Resolution getNearestResolution(int pixel) {

        List<Resolution> results = resolutionRepository.findResolutionByPixelLessThanLimit(pixel);
        return results.isEmpty() ? null : results.get(0);
    }

    public Resolution getFileResolution(File file) throws IOException {

        ProcessBuilder builder = new ProcessBuilder(
                "ffprobe",
                "-v", "error",
                "-select_streams", "v:0",
                "-show_entries", "stream=height",
                "-of", "csv=p=0",
                file.toString()
        );

        Process process = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String heightStr = reader.readLine();
            int exitCode = process.waitFor();

            if (exitCode != 0 || heightStr == null) {
                throw new RuntimeException("Failed to get video height.");
            }

            int pixel = Integer.parseInt(heightStr.trim());
            return getNearestResolution(pixel);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
