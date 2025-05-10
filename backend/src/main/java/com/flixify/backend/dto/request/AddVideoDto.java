package com.flixify.backend.dto.request;

import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddVideoDto {

    private String title;

    private Double duration;

    private Long size;

    private Integer userId;

    private UUID uniqueId;

    private VideoSplitterRule videoSplitterRule;

    public Video toVideo() {
        Video video = new Video();
        video.setTitle(title);
        video.setDuration(duration);
        video.setSize(size);
        video.setFileId(uniqueId);
        video.setRule(videoSplitterRule);
        return video;
    }
}
