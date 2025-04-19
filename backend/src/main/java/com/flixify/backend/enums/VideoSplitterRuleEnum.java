package com.flixify.backend.enums;

public enum VideoSplitterRuleEnum {

    SPLIT_BY_DURATION(1, "SPLIT_BY_DURATION", "Split by duration"),
    SPLIT_BY_SIZE(2, "SPLIT_BY_SIZE", "Split by size"),
    SPLIT_BY_CHUNK_COUNT(3, "SPLIT_BY_CHUNK_COUNT", "Split by chunk count");

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
