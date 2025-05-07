package com.flixify.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Chunk extends Auditable {

    private Integer chunkId;

    @ManyToOne
    private Video video;

    @ManyToOne
    private Resolution resolution;

    @Column(nullable = false)
    private Double startTime;

    @Column(nullable = false)
    private Double endTime;

    @Column(nullable = false)
    private String fileId;    // UUID = Name of the file

    @ManyToOne
    private ChunkStatus chunkStatus;

    private Double size;
}
