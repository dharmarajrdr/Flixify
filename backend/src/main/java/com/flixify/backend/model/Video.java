package com.flixify.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Video extends Auditable {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Double duration;

    @Column(nullable = false)
    private Long size;

    private Integer chunkCount;

    @OneToOne
    private User owner;
}
