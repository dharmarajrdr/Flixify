package com.flixify.backend.seeder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.flixify.backend.enums.VideoSplitterRuleEnum;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.repository.VideoSplitterRuleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VideoSplitterRuleSeeder implements ApplicationRunner {

    @Autowired
    private VideoSplitterRuleRepository videoSplitterRuleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (videoSplitterRuleRepository.count() == 0) {
            List<VideoSplitterRule> resolutions = Arrays.stream(VideoSplitterRuleEnum.values())
                    .map(ruleEnum -> new VideoSplitterRule(ruleEnum.getName(), ruleEnum.getDescription()))
                    .collect(Collectors.toList());

            videoSplitterRuleRepository.saveAll(resolutions);
        }
    }
}
