package com.flixify.backend.seeder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.flixify.backend.enums.VideoSplitterRuleEnum;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.repository.VideoSplitterRuleRepository;

@Component
@AllArgsConstructor
public class VideoSplitterRuleSeeder implements ApplicationRunner {

    private final VideoSplitterRuleRepository videoSplitterRuleRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (videoSplitterRuleRepository.count() == 0) {
            List<VideoSplitterRule> resolutions = Arrays.stream(VideoSplitterRuleEnum.values())
                    .map(ruleEnum -> new VideoSplitterRule(ruleEnum.getName(), ruleEnum.getDescription()))
                    .collect(Collectors.toList());

            videoSplitterRuleRepository.saveAll(resolutions);
        }
    }
}
