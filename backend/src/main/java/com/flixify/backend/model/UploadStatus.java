package com.flixify.backend.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class UploadStatus extends BaseModel {

    private String status;

    private String description;

    public UploadStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }
}
