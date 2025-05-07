package com.flixify.backend.enums;

public enum VideoSplitterRuleEnum {

    SPLIT_BY_DURATION(1, "DURATION_BASED_VIDEO_SPLITTER", "Split by duration"),
    SPLIT_BY_SIZE(2, "SIZE_BASED_VIDEO_SPLITTER", "Split by size"),
    SPLIT_BY_CHUNK_COUNT(3, "COUNT_BASED_VIDEO_SPLITTER", "Split by chunk count");

    private final int id;
    private final String name;
    private final String description;

    VideoSplitterRuleEnum(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }
}
