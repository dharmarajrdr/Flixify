package com.flixify.backend.model;

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

    private String title;

    private Double duration;

    private Long size;

    private Integer chunkCount;

    @OneToOne
    private User owner;
}
