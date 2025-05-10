package com.flixify.backend.service.implementations;

import com.flixify.backend.config.PathConfig;
import com.flixify.backend.custom_exceptions.VideoUploadFailed;
import com.flixify.backend.dto.request.VideoUploadRequestDto;
import com.flixify.backend.service.interfaces.*;
import com.flixify.backend.util.Generator;
import com.flixify.backend.util.LocalDisk;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VideoUploaderImpl implements VideoUploaderService {

    private final VideoChunkTranscoderService videoChunkTranscoderService;

    /**
     * Saves the given file in disk
     *
     * @param videoFile
     * @return Path
     * @throws IOException
     */
    private Path storeInDisk(MultipartFile videoFile, String fileName) throws IOException {

        Path uploadPath = Paths.get(PathConfig.VIDEO_STORAGE_DIRECTORY);
        Files.createDirectories(uploadPath);
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, videoFile.getBytes());
        return filePath;
    }

    @Override
    @Transactional
    public Path upload(MultipartFile videoFile, VideoUploadRequestDto videoUploadRequestDto) {

        Path filePath = null;

        try {

            UUID uniqueId = Generator.generateUUID();
            filePath = storeInDisk(videoFile, uniqueId.toString() + ".mp4");

            videoChunkTranscoderService.splitUploadedVideIntoDifferentResolutions(videoFile, filePath, videoUploadRequestDto, uniqueId);

            return filePath;

        } catch (IOException e) {
            LocalDisk.deleteFile(filePath);
            throw new VideoUploadFailed(e);
        } catch (InterruptedException e) {
            LocalDisk.deleteFile(filePath);
            throw new RuntimeException(e);
        }
    }
}
