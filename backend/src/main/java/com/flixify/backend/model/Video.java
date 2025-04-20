package com.flixify.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Video extends Auditable {

    private String title;

    private Double duration;

    private Long size;

    private Integer chunkCount;

    @OneToOne
    private User owner;

    public Video(String title, Double duration, Long size, Integer chunkCount, User owner) {
        this.title = title;
        this.duration = duration;
        this.size = size;
        this.chunkCount = chunkCount;
        this.owner = owner;
    }
}
