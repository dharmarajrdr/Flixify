package com.flixify.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoSplitterRuleEnum {

    SPLIT_BY_DURATION(1, "DURATION_BASED_VIDEO_SPLITTER", "Split by duration"),
    SPLIT_BY_SIZE(2, "SIZE_BASED_VIDEO_SPLITTER", "Split by size"),
    SPLIT_BY_CHUNK_COUNT(3, "COUNT_BASED_VIDEO_SPLITTER", "Split by chunk count");

    private final int id;
    private final String name;
    private final String description;

}
