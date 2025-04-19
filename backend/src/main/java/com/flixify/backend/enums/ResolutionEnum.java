package com.flixify.backend.enums;

public enum ResolutionEnum {

    P_144("144p", 144),
    P_240("240p", 240),
    P_360("360p", 360),
    P_480("480p", 480),
    P_720("720p", 720),
    P_1080("1080p", 1080),
    P_1440("1440p", 1440),
    P_2160("2160p", 2160);

    private final String title;
    private final Integer pixel;

    ResolutionEnum(String title, Integer pixel) {
        this.title = title;
        this.pixel = pixel;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPixel() {
        return pixel;
    }
}
