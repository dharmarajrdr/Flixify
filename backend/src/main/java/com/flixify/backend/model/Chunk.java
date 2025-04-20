package com.flixify.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Chunk extends Auditable {

    private Integer chunkId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Video video;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resolution resolution;

    @Column(nullable = false)
    private Double startTime;

    @Column(nullable = false)
    private Double endTime;

    @Column(nullable = false)
    private String fileId;    // UUID = Name of the file

    @OneToOne
    private ChunkStatus chunkStatus;

    private Double size;
}
