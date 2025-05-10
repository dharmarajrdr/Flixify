package com.flixify.backend.seeder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.flixify.backend.enums.ResolutionEnum;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.repository.ResolutionRepository;

@Component
@AllArgsConstructor
public class ResolutionSeeder implements ApplicationRunner {

    private final ResolutionRepository resolutionRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (resolutionRepository.count() == 0) {
            List<Resolution> resolutions = Arrays.stream(ResolutionEnum.values())
                    .map(resEnum -> new Resolution(resEnum.getTitle(), resEnum.getPixel()))
                    .collect(Collectors.toList());

            resolutionRepository.saveAll(resolutions);
        }
    }
}
