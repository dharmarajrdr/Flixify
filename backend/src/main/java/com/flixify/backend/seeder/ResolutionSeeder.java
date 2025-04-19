package com.flixify.backend.seeder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.flixify.backend.enums.ResolutionEnum;
import com.flixify.backend.model.Resolution;
import com.flixify.backend.repository.ResolutionRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResolutionSeeder implements ApplicationRunner {

    @Autowired
    private ResolutionRepository resolutionRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (resolutionRepository.count() == 0) {
            List<Resolution> resolutions = Arrays.stream(ResolutionEnum.values())
                    .map(resEnum -> new Resolution(resEnum.getTitle(), resEnum.getPixel()))
                    .collect(Collectors.toList());

            resolutionRepository.saveAll(resolutions);
        }
    }
}
