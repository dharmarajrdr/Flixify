package com.flixify.backend.factory;

import com.flixify.backend.custom_exceptions.InvalidEnumExpected;
import com.flixify.backend.model.VideoSplitterRule;
import com.flixify.backend.service.VideoSplitterService;
import com.flixify.backend.strategy.VideoSplitter.CountBasedVideoSplitter;
import com.flixify.backend.strategy.VideoSplitter.DurationBasedVideoSplitter;
import com.flixify.backend.strategy.VideoSplitter.SizeBasedVideoSplitter;

public class VideoSplitterFactory {

    public static VideoSplitterService getVideoSplitter(VideoSplitterRule videoSplitterRule) {

        String name = videoSplitterRule.getName();

        switch (name) {

            /**
             * Each chunk will have 'x' seconds/minutes
             */
            case "DURATION_BASED_VIDEO_SPLITTER": {

                return new DurationBasedVideoSplitter();
            }

            /**
             * Each chunk will have 'x' kilobytes/megabytes
             */
            case "SIZE_BASED_VIDEO_SPLITTER": {

                return new SizeBasedVideoSplitter();
            }

            /**
             * File will be split into 'N' chunks
             */
            case "COUNT_BASED_VIDEO_SPLITTER": {

                return new CountBasedVideoSplitter();
            }

            default: {

                throw new InvalidEnumExpected("Implementation for rule '" + name + "' does not exist.");
            }
        }
    }
}
