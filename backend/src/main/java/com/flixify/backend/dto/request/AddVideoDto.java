package com.flixify.backend.dto.request;

import com.flixify.backend.model.Video;
import lombok.Data;

import java.util.UUID;

@Data
public class AddVideoDto {

    private String title;

    private Double duration;

    private Long size;

    private Integer userId;

    private UUID uniqueId;

    public Video toVideo() {
        Video video = new Video();
        video.setTitle(title);
        video.setDuration(duration);
        video.setSize(size);
        video.setUniqueId(uniqueId);
        return video;
    }
}
