package com.flixify.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MediaDownloads extends BaseModel {

    @ManyToOne
    private Video video;

    @ManyToOne
    private Chunk chunk;

    @ManyToOne
    private Resolution resolution;

    private long downloads;

    public MediaDownloads(Video video, Chunk chunk, Resolution resolution) {
        this.video = video;
        this.chunk = chunk;
        this.resolution = resolution;
    }
}
