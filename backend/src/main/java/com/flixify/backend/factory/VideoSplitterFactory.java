package com.flixify.backend.factory;

import com.flixify.backend.custom_exceptions.InvalidEnumExpected;
import com.flixify.backend.service.interfaces.VideoSplitterService;
import com.flixify.backend.strategy.VideoSplitter.CountBasedVideoSplitter;
import com.flixify.backend.strategy.VideoSplitter.DurationBasedVideoSplitter;
import com.flixify.backend.strategy.VideoSplitter.SizeBasedVideoSplitter;

public class VideoSplitterFactory {

    public static VideoSplitterService getVideoSplitter(String ruleName) {

        switch (ruleName) {

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

                throw new InvalidEnumExpected("Implementation for rule '" + ruleName + "' does not exist.");
            }
        }
    }
}
