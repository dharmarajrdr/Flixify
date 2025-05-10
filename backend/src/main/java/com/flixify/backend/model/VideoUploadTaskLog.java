package com.flixify.backend.model;

import com.flixify.backend.enums.VideoUploadStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class VideoUploadTaskLog extends Auditable {

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Enumerated(EnumType.STRING)
    private VideoUploadStatusEnum status;
}
