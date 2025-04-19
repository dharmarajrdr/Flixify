package com.flixify.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class VideoSplitter extends BaseModel {

    @OneToOne
    private Video video;

    @OneToOne
    private VideoSplitterRule rule;
}
