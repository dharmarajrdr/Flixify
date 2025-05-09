package com.flixify.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.flixify.backend.model.Chunk;
import com.flixify.backend.model.Video;
import com.flixify.backend.model.VideoSplitterRule;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDto {

    private UUID fileId;

    private String title;

    private Double duration;

    private Long size;

    private LocalDateTime createdAt;

    @JsonIgnore
    private List<Chunk> chunks;

    private Integer chunkCount;

    private VideoSplitterRule rule;

    public static VideoDto fromVideo(Video video) {

        VideoDto videoDto = new VideoDto();
        videoDto.setTitle(video.getTitle());
        videoDto.setFileId(video.getFileId());
        videoDto.setDuration(video.getDuration());
        videoDto.setSize(video.getSize());
        videoDto.setChunkCount(video.getChunkCount());
        videoDto.setChunks(video.getChunks());
        videoDto.setCreatedAt(video.getCreatedAt());
        videoDto.setRule(video.getRule());
        return videoDto;
    }
}
