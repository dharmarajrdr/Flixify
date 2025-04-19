package com.flixify.backend.model;

import jakarta.persistence.Entity;
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
}
