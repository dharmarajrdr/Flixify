package com.flixify.backend.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MediaDownloads extends BaseModel {

    @ManyToOne(cascade = CascadeType.ALL)
    private Video video;

    @ManyToOne(cascade = CascadeType.ALL)
    private Chunk chunk;

    @ManyToOne(cascade = CascadeType.ALL)
    private Resolution resolution;

    private long downloads;

    public MediaDownloads(Video video, Chunk chunk, Resolution resolution) {
        this.video = video;
        this.chunk = chunk;
        this.resolution = resolution;
    }
}
