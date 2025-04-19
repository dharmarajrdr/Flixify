package com.flixify.backend.seeder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import com.flixify.backend.enums.UploadStatusEnum;
import com.flixify.backend.model.UploadStatus;
import com.flixify.backend.repository.UploadStatusRepository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UploadStatusSeeder implements ApplicationRunner {

    @Autowired
    private UploadStatusRepository UploadStatusRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (UploadStatusRepository.count() == 0) {
            List<UploadStatus> UploadStatus = Arrays.stream(UploadStatusEnum.values())
                    .map(resEnum -> new UploadStatus(resEnum.getStatus(), resEnum.getDescription()))
                    .collect(Collectors.toList());

            UploadStatusRepository.saveAll(UploadStatus);
        }
    }
}
