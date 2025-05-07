package com.flixify.backend.service;

import com.flixify.backend.custom_exceptions.InvalidEnumExpected;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.repository.VideoSplitterRuleRepository;
import org.springframework.stereotype.Service;

@Service
public class VideoSplitterRuleServiceImpl implements VideoSplitterRuleService {

    private final VideoSplitterRuleRepository videoSplitterRuleRepository;

    public VideoSplitterRuleServiceImpl(VideoSplitterRuleRepository videoSplitterRuleRepository) {
        this.videoSplitterRuleRepository = videoSplitterRuleRepository;
    }

    @Override
    public VideoSplitterRule getVideoSplitterRule(String ruleName) {

        return this.videoSplitterRuleRepository.findByName(ruleName).orElseThrow(() -> new InvalidEnumExpected("Video Splitter Rule '" + ruleName + "' is a invalid."));
    }
}
