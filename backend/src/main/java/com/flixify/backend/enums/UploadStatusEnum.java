package com.flixify.backend.enums;

public enum UploadStatusEnum {

    IN_PROGRESS("In Progress", "The upload is currently in progress."),
    COMPLETED("Completed", "The upload has been completed successfully."),
    FAILED("Failed", "The upload has failed."),
    CANCELLED("Cancelled", "The upload has been cancelled."),
    PENDING("Pending", "The upload is pending and has not started yet."),
    DELETED("Deleted", "The upload has been deleted."),
    PROCESSING("Processing", "The upload is being processed."),
    ERROR("Error", "An error occurred during the upload process."),
    TIMEOUT("Timeout", "The upload has timed out."),
    QUEUED("Queued", "The upload is queued and waiting to be processed."),
    RETRY("Retry", "The upload is being retried after a failure."),
    PARTIALLY_UPLOADED("Partially Uploaded", "The upload has been partially completed."),
    INVALID("Invalid", "The upload is invalid and cannot be processed."),
    UNKNOWN("Unknown", "The upload status is unknown.");

    private String status;

    private String description;

    UploadStatusEnum(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
