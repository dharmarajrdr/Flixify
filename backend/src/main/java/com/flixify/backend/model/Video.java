package com.flixify.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Video extends Auditable {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private UUID uniqueId;

    @Column(nullable = false)
    private Double duration;

    @Column(nullable = false)
    private Long size;

    private Integer chunkCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "owner_id")
    @JsonIgnore // To avoid circular reference
    private User owner;

    @OneToMany(mappedBy = "video")
    private List<Chunk> chunks;

    @ManyToOne
    private VideoSplitterRule rule;
}
