package com.flixify.backend.seeder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.flixify.backend.enums.ChunkStatusEnum;
import com.flixify.backend.model.ChunkStatus;
import com.flixify.backend.repository.ChunkStatusRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChunkStatusSeeder implements ApplicationRunner {

    @Autowired
    private ChunkStatusRepository ChunkStatusRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (ChunkStatusRepository.count() == 0) {
            List<ChunkStatus> ChunkStatuss = Arrays.stream(ChunkStatusEnum.values())
                    .map(resEnum -> new ChunkStatus(resEnum.getStatus(), resEnum.getDescription()))
                    .collect(Collectors.toList());

            ChunkStatusRepository.saveAll(ChunkStatuss);
        }
    }
}
