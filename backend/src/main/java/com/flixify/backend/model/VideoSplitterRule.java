package com.flixify.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class VideoSplitterRule extends BaseModel {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;
}
