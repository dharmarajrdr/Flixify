package com.flixify.backend.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class VideoUpload extends Auditable {

    @OneToOne
    private Video video;

    private Date startTime;

    private Date completionTime;

    @OneToOne
    private UploadStatus status;
}
