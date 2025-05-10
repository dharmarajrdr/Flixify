package com.flixify.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Chunk extends Auditable {

    private Integer chunkId;

    @ManyToOne
    @JsonIgnore
    private Video video;

    @ManyToOne
    private Resolution resolution;

    @Column(nullable = false)
    private Double startTime;

    @Column(nullable = false)
    private Double endTime;

    private Double size;
}
