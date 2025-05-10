package com.flixify.backend.seeder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.flixify.backend.enums.VideoUploadStatusEnum;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.flixify.backend.model.UploadStatus;
import com.flixify.backend.repository.UploadStatusRepository;

import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UploadStatusSeeder implements ApplicationRunner {

    private final UploadStatusRepository UploadStatusRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (UploadStatusRepository.count() == 0) {
            List<UploadStatus> UploadStatus = Arrays.stream(VideoUploadStatusEnum.values())
                    .map(resEnum -> new UploadStatus(resEnum.getStatus(), resEnum.getDescription()))
                    .collect(Collectors.toList());

            UploadStatusRepository.saveAll(UploadStatus);
        }
    }
}
