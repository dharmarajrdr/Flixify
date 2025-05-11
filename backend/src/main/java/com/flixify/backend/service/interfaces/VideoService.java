package com.flixify.backend.service.interfaces;

import com.flixify.backend.dto.request.AddVideoDto;
import com.flixify.backend.model.Video;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface VideoService {

    public Boolean isOwner(Integer userId, UUID fileId);

    public double getVideoDuration(File file) throws IOException, InterruptedException;

    public List<Video> getVideosByUserId(Integer userId);

    public List<Video> getVideosInTrashByUserId(Integer userId);

    public List<Video> getDeletedAndLastUpdatedAtBefore(LocalDateTime date);

    public Video addVideo(AddVideoDto addVideoDto);

    public Video getVideo(Integer userId, UUID fileId);

    public void deleteVideo(Integer userId, UUID videoId);

    public void recoverVideo(Integer userId, UUID fileId);
}
