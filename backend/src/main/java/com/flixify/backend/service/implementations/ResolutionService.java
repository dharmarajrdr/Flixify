package com.flixify.backend.service.implementations;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.ChunkDirectoryMissing;
import com.flixify.backend.custom_exceptions.ResolutionAlreadyExist;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.model.Video;
import com.flixify.backend.repository.ResolutionRepository;
import com.flixify.backend.service.interfaces.ResolutionConverterService;
import com.flixify.backend.util.LocalDisk;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

@Service
public class ResolutionService {

    private final ResolutionRepository resolutionRepository;
    private final ResolutionConverterService resolutionConverterService;

    public ResolutionService(ResolutionRepository resolutionRepository, ResolutionConverterService resolutionConverterService) {
        this.resolutionRepository = resolutionRepository;
        this.resolutionConverterService = resolutionConverterService;
    }

    public List<Resolution> getAllResolutionsLessThanPixel(int pixel) {

        return resolutionRepository.findResolutionByPixelLessThanLimit(pixel);
    }

    private Resolution getNearestResolution(int pixel) {

        List<Resolution> results = getAllResolutionsLessThanPixel(pixel);
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

    public File transcodeChunks(Resolution resolutionToTranscode, UUID uniqueId, Resolution rawResolution, Video video) {

        File rawChunksDirectory = new File(PathConfig.CHUNK_STORAGE_DIRECTORY + "/" + uniqueId + "/" + rawResolution.getTitle());
        File resolutionToTranscodeDirectory = new File(PathConfig.CHUNK_STORAGE_DIRECTORY + "/" + uniqueId + "/" + resolutionToTranscode.getTitle());

        try {

            if (!rawChunksDirectory.exists()) {
                throw new ChunkDirectoryMissing(uniqueId, rawResolution);
            }
            if (resolutionToTranscodeDirectory.exists()) {
                throw new ResolutionAlreadyExist(resolutionToTranscode, resolutionToTranscodeDirectory);
            }

            LocalDisk.createDirectoryIfNotExists(resolutionToTranscodeDirectory);

            File[] rawChunks = rawChunksDirectory.listFiles((dir, fileName) -> fileName.endsWith(".mp4"));
            if (rawChunks == null) {
                throw new ChunkDirectoryMissing(uniqueId, rawResolution);
            }

            int chunkId = 1;
            for (File sourceFile : rawChunks) {

                File targetFile = new File(resolutionToTranscodeDirectory.getAbsolutePath(), (chunkId++) + ".mp4");
                resolutionConverterService.convertResolution(resolutionToTranscode, sourceFile, targetFile);
            }

            return  resolutionToTranscodeDirectory;

        } catch (Exception e) {
            LocalDisk.deleteDirectory(resolutionToTranscodeDirectory.toPath()); // Delete the chunks directory if any issue
            throw new RuntimeException(e);
        }
    }

}
