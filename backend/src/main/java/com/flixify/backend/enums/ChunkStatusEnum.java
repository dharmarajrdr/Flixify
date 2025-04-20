package com.flixify.backend.enums;

import com.flixify.backend.custom_exceptions.InvalidEnumExpected;
import com.flixify.backend.model.ChunkStatus;

public enum ChunkStatusEnum {

    COMPLETED("Completed", "Chunk has been processed successfully"),
    FAILED("Failed", "Chunk processing failed"),
    CANCELLED("Cancelled", "Chunk processing was cancelled"),
    IN_PROGRESS("In Progress", "Chunk is currently being processed"),
    DELETED("Deleted", "Chunk has been deleted"),
    PROCESSING("Processing", "Chunk is being processed"),
    ERROR("Error", "An error occurred during chunk processing"),
    TIMEOUT("Timeout", "Chunk processing timed out"),
    QUEUED("Queued", "Chunk is queued for processing"),
    RETRY("Retry", "Chunk processing is being retried after a failure"),
    PARTIALLY_UPLOADED("Partially Uploaded", "Chunk has been partially uploaded"),
    INVALID("Invalid", "Chunk is invalid and cannot be processed"),
    UNKNOWN("Unknown", "Chunk status is unknown");

    private String status;

    private String description;

    ChunkStatusEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public static ChunkStatus fromChunkStatus(String status) {

        for (ChunkStatusEnum chunkStatusEnum : ChunkStatusEnum.values()) {
            if (chunkStatusEnum.getStatus().equalsIgnoreCase(status)) {
                return new ChunkStatus(chunkStatusEnum.getStatus(), chunkStatusEnum.getDescription());
            }
        }
        throw new InvalidEnumExpected("Status '" + status + "' is not a valid ChunkStatusEnum");
    }
}
