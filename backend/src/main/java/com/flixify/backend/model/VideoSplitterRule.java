package com.flixify.backend.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class VideoSplitterRule extends BaseModel {

    private String name;

    private String description;

    public VideoSplitterRule(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
