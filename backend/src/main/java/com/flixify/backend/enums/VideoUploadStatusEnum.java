package com.flixify.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoUploadStatusEnum {

    RAW_VIDEO_UPLOADING("RAW_VIDEO_UPLOADING", "The raw video is currently being uploaded."),
    RAW_VIDEO_UPLOADED("RAW_VIDEO_UPLOADED", "The raw video has been uploaded successfully."),

    SPLITTING_VIDEO("SPLITTING_VIDEO", "The uploaded video is being split into smaller chunks."),
    VIDEO_SPLIT_SUCCESS("VIDEO_SPLIT_SUCCESS", "The video has been successfully split into chunks."),

    RESOLUTION_CONVERTING("RESOLUTION_CONVERTING", "Video chunks are being transcoded into multiple resolutions."),
    RESOLUTION_CONVERTED("RESOLUTION_CONVERTED", "All video chunks have been transcoded successfully."),

    MANIFEST_GENERATING("MANIFEST_GENERATING", "The manifest file is being generated."),
    MANIFEST_GENERATED("MANIFEST_GENERATED", "The manifest file has been generated successfully."),

    COMPLETED("COMPLETED", "Video processing has been completed successfully."),
    FAILED("FAILED", "An error occurred during the video upload or processing.");

    private final String status;

    private final String description;
}
